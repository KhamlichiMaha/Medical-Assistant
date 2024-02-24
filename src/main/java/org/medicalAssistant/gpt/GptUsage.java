package org.medicalAssistant.gpt;

public class GptUsage {
	public int completionTokens;
	public int promptTokens;
	public int totalTokens;

	public int getCompletionTokens(){
		return completionTokens;
	}

	public int getPromptTokens(){
		return promptTokens;
	}

	public int getTotalTokens(){
		return totalTokens;
	}
}
