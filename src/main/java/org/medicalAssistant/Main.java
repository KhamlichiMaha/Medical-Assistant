package org.medicalAssistant;

import org.medicalAssistant.gpt.ChatGptApi;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ChatGptApi chatGptApi = new ChatGptApi();
        String prompt = chatGptApi.prompt("How are you?");
        System.out.println(prompt);
    }
}