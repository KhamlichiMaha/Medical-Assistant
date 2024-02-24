package org.medicalAssistant.gpt;

public class GptMessage {
    public String role;
    public String content;

    public GptMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
}