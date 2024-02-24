package org.medicalAssistant.gpt;

public class GptChoicesItem {
	public String finishReason;
	public int index;
	public GptMessage message;

	public String getFinishReason(){
		return finishReason;
	}

	public int getIndex(){
		return index;
	}

	public GptMessage getMessage(){
		return message;
	}
}
