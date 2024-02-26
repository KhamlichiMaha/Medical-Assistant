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

public class p01_TreatmentSuggestions {
    public static void main(String[] args) throws IOException, InterruptedException {
        OpenAiService service = new OpenAiService(args[0]);

        // Creates the assistant: ONE assistant to be created
        AssistantRequest assistantRequest = AssistantRequest.builder()
                .model("gpt-3.5-turbo")
                .name("Dentist Assistant")
                .instructions("Tu es l'assistant d'un dentiste qui voit des patients. Réponds uniquement par écrit.")
                .build();
        Assistant assistant = service.createAssistant(assistantRequest);

        // Creates thread: One thread per patient
        ThreadRequest threadRequest = ThreadRequest.builder()
                .messages(List.of(new MessageRequest(
                        "user",
                        "Je m'aprête a faire une chirurgie buccale pour extraire un dent de sagesse inférieure chez un jeune homme de 18 ans qui suit un traitement pour une insuffisance rénale." +
                                "Comment introduire l'aiguille de l'anesthésie tronculaire a coté de l'épine de spix?",
                        List.of(),
                        Map.of()
                )))
                .build();
        Thread thread = service.createThread(threadRequest);

        // Creates run for treatment solution suggestions
        RunCreateRequest runCreateRequest = RunCreateRequest.builder()
                .assistantId(assistant.getId())
                .instructions("Considérant le patient décrit et ses conditions médicales, répond aux questions du dentiste" +
                        " Donne le plus de détails possibles et des exemples.")
                .build();
        Run run = service.createRun(thread.getId(), runCreateRequest);

        // Checks if the run is queued or in progress. If it is, wait 1000ms and check again.
        // If not it means the run is requires_action, cancelling, cancelled, failed, completed, or expired.
        while (run.getStatus().equals("queued") || run.getStatus().equals("in_progress")) {
            sleep(1000);
            run = service.retrieveRun(thread.getId(), run.getId());
        }

        // Prints responses
        OpenAiResponse<Message> messages = service.listMessages(thread.getId());
        messages.data.forEach(message ->
                message.getContent().forEach(content ->
                        System.out.println(content.getText())
                ));

    }
}