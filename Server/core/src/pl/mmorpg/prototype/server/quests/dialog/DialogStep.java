package pl.mmorpg.prototype.server.quests.dialog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import pl.mmorpg.prototype.server.quests.QuestMakerIgnore;

@JsonSerialize
@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class DialogStep implements Serializable
{
	@JsonProperty @QuestMakerIgnore
	private final Map<String, DialogStep> furtherDialogs = new HashMap<>(); 
	private final String speech;

	public DialogStep(String speech)
	{
		this.speech = speech;
	}
	
	@JsonCreator
	public DialogStep(@JsonProperty("speech") String speech, 
			@JsonProperty("furtherDialogs") Map<String, DialogStep> furtherDialogs)
	{
		this.speech = speech;
		if(furtherDialogs != null)
			this.furtherDialogs.putAll(furtherDialogs);
	}
	
	@JsonIgnore
	public Set<String> getPossibleAnswers()
	{
		return furtherDialogs.keySet();
	}

	public DialogStep getNextDialogStep(String anwser)
	{
		return furtherDialogs.get(anwser);
	}
	
	public String getSpeech()
	{
		return speech; 
	}
	
	@JsonIgnore
	public boolean isLastStep()
	{
		return furtherDialogs.isEmpty();
	}

}
