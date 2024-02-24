package org.medicalAssistant.gpt;

import java.util.List;

public class GptResponse {
	public int created;
	public GptUsage usage;
	public String model;
	public String id;
	public List<GptChoicesItem> choices;
	public String object;

	public int getCreated(){
		return created;
	}

	public GptUsage getUsage(){
		return usage;
	}

	public String getModel(){
		return model;
	}

	public String getId(){
		return id;
	}

	public List<GptChoicesItem> getChoices(){
		return choices;
	}

	public String getObject(){
		return object;
	}
}