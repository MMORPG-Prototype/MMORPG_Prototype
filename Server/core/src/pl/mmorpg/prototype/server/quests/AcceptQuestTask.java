package pl.mmorpg.prototype.server.quests;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mmorpg.prototype.server.quests.events.AcceptQuestEvent;

public class AcceptQuestTask extends QuestTaskBase<AcceptQuestEvent>
{
	@QuestMakerIgnore
	private boolean finished = false;

	private final String questName;

	public AcceptQuestTask(@JsonProperty("parentIndex") Integer parentIndex,
	                       @JsonProperty("questName") String questName)
	{
		super(parentIndex, AcceptQuestEvent.class);
		this.questName = questName;
	}

	@Override
	public boolean isApplicable(AcceptQuestEvent event)
	{
		return event.getQuestName().equalsIgnoreCase(questName);
	}

	@Override
	public void apply(AcceptQuestEvent event)
	{
		finished = true;
	}

	@Override
	public boolean isFinished()
	{
		return finished;
	}

	@Override
	public String getDescription()
	{
		return "Accept quest";
	}

	@Override
	public float getPercentFinished()
	{
		return finished ? 100.0f : 0.0f;
	}

}
