package org.medicalAssistant.gpt;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ChatGptApi {
    private final String API_KEY = "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";
    private static final Gson gson = new Gson();

    private GptResponse call(GptRequest gptRequest) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(gptRequest)))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), GptResponse.class);
    }

    public String prompt(String message) throws IOException, InterruptedException {
        GptResponse response = call(new GptRequest(
                "gpt-3.5-turbo",
                List.of(new GptMessage(
                        "user",
                        message
                ))
        ));
        return response.getChoices().get(0).getMessage().content;
    }

}
