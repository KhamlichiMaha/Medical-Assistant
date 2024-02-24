package org.medicalAssistant.gpt;

import java.util.List;

public class GptRequest {
    public String model;
    public List<GptMessage> messages;

    public GptRequest(String model, List<GptMessage> messages) {
        this.model = model;
        this.messages = messages;
    }

}
