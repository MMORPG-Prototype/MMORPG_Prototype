package pl.mmorpg.prototype.server.quests.dialog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import pl.mmorpg.prototype.clientservercommon.packets.quest.event.EventPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.quests.QuestMakerIgnore;
import pl.mmorpg.prototype.server.quests.QuestTaskBase;
import pl.mmorpg.prototype.server.quests.events.NpcDialogEvent;

public class NpcDialogTask extends QuestTaskBase<NpcDialogEvent>
{
	@JsonProperty
	private final String npcName;
	@JsonProperty
	private final DialogStep dialogEntryPoint;
	@JsonIgnore
	@QuestMakerIgnore
	private DialogStep currentDialogStep;
	@QuestMakerIgnore
	private boolean isFinished = false;

	@JsonCreator
	public NpcDialogTask(@JsonProperty("parentIndex") Integer parentIndex,
	                     @JsonProperty("npcName") String npcName,
	                     @JsonProperty("dialogEntryPoint") DialogStep dialogEntryPoint)
	{
		super(parentIndex, NpcDialogEvent.class);
		this.npcName = npcName;
		this.dialogEntryPoint = dialogEntryPoint;
		this.currentDialogStep = dialogEntryPoint;
	}

	@Override
	public boolean isApplicable(NpcDialogEvent event)
	{
		return event.getNpc().getName().equals(npcName)
				&& (event.isDialogStarting() || currentDialogStep.getPossibleAnswers().contains(event.getAnswer()));
	}

	@Override
	public EventPacket apply(NpcDialogEvent event)
	{
		currentDialogStep = getNextDialogStep(event);
		if (currentDialogStep.isLastStep())
			isFinished = true;
		return PacketsMaker.makeNpcDialogEventPacket(getSourceTask(),
				currentDialogStep.getSpeech(), currentDialogStep.getPossibleAnswers(), event);
	}

	private DialogStep getNextDialogStep(NpcDialogEvent event)
	{
		if (event.isDialogStarting())
			return dialogEntryPoint;
		else
			return currentDialogStep.getNextDialogStep(event.getAnswer());
	}

	@Override
	public String getDescription()
	{
		return "Go talk with " + npcName;
	}

	@Override
	public float getPercentFinished()
	{
		return isFinished ? 100.0f : 0.0f;
	}

}
