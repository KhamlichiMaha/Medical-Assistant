package org.medicalAssistant;

import com.theokanning.openai.OpenAiResponse;
import com.theokanning.openai.assistants.Assistant;
import com.theokanning.openai.assistants.AssistantRequest;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.messages.MessageRequest;
import com.theokanning.openai.runs.Run;
import com.theokanning.openai.runs.RunCreateRequest;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.threads.Thread;
import com.theokanning.openai.threads.ThreadRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class p02_TreatmentInstructions {
    public static void main(String[] args) throws IOException, InterruptedException {
        OpenAiService service = new OpenAiService(args[0]);

        AssistantRequest assistantRequest = AssistantRequest.builder()
                .model("gpt-3.5-turbo")
                .name("Dentist Assistant")
                .instructions("You are the assistant of a dentist who is taking patients and is mid-consultation. You only answer in text.")
                .build();
        Assistant assistant = service.createAssistant(assistantRequest);

        ThreadRequest threadRequest = ThreadRequest.builder()
                .messages(List.of(new MessageRequest(
                        "user",
                        "I am taking patient Maha who has a cavity on her bottom rightmost incisor \n" +
                                "I am choosing to use the following treatment suggestion: \"perform a root canal treatment if the cavity has reached the pulp of the tooth\"",
                        List.of(),
                        Map.of()
                )))
                .build();
        Thread thread = service.createThread(threadRequest);

        RunCreateRequest runCreateRequest = RunCreateRequest.builder()
                .assistantId(assistant.getId())
                .instructions("Provide a list of materials and products necessary for the chosen treatment. " +
                        "Only include items that are specific to the chosen treatment.")
                .build();
        Run run = service.createRun(thread.getId(), runCreateRequest);

        while (run.getStatus().equals("queued") || run.getStatus().equals("in_progress")) {
            sleep(1000);
            run = service.retrieveRun(thread.getId(), run.getId());
        }

        OpenAiResponse<Message> messages = service.listMessages(thread.getId());
        messages.data.forEach(message ->
                message.getContent().forEach(content ->
                        System.out.println(content.getText())
                ));

    }
}