package pl.mmorpg.prototype.server.quests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mmorpg.prototype.clientservercommon.packets.quest.event.EventPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.quests.events.AcceptQuestEvent;

public class AcceptQuestTask extends QuestTaskBase<AcceptQuestEvent>
{
	@QuestMakerIgnore
	private boolean finished = false;

	private final String questName;

	@JsonCreator
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
	public EventPacket apply(AcceptQuestEvent event)
	{
		finished = true;
		return PacketsMaker.makeQuestAcceptedEventPacket(getSourceTask());
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
