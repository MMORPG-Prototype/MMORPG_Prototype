package pl.mmorpg.prototype.server.quests.dialog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.npcs.Npc;
import pl.mmorpg.prototype.server.quests.QuestTaskBase;
import pl.mmorpg.prototype.server.quests.events.NpcDialogEvent;

public class NpcDialogTask extends QuestTaskBase<NpcDialogEvent>
{
	@JsonProperty
	private final String npcName;
	@JsonProperty
	private final DialogStep dialogEntryPoint;
	@JsonIgnore
	private DialogStep currentDialogStep;
	private boolean isFinished = false;

	@JsonCreator
	public NpcDialogTask(@JsonProperty("npcName") String npcName,
			@JsonProperty("dialogEntryPoint") DialogStep dialogEntryPoint)
	{
		super(NpcDialogEvent.class);
		this.npcName = npcName;
		this.dialogEntryPoint = dialogEntryPoint;
		this.currentDialogStep = dialogEntryPoint;
	}

	@Override
	public boolean isApplicable(NpcDialogEvent event)
	{
		return event.getNpc().getName().equals(npcName)
				&& (event.isDialogStarting() || currentDialogStep.getPossibleAnswers().contains(event.getAnwser()));
	}

	@Override
	public void apply(NpcDialogEvent event)
	{
		int connectionId = event.getReceivers().get(0).getConnectionId();
		if (event.isDialogStarting())
		{
			currentDialogStep = dialogEntryPoint;
			sendPacket(event.getPacketsSender(), connectionId, event.getNpc(), dialogEntryPoint);
		}
		else
		{
			currentDialogStep = currentDialogStep.getNextDialogStep(event.getAnwser());
			if (currentDialogStep.isLastStep())
				isFinished = true;
			sendPacket(event.getPacketsSender(), connectionId, event.getNpc(), currentDialogStep);
		}
	}

	private void sendPacket(PacketsSender packetsSender, int connectionId, Npc npc, DialogStep dialogStep)
	{
		packetsSender.sendTo(connectionId, PacketsMaker.makeNpcStartDialogPacket(npc.getId(), dialogStep.getSpeech(),
				dialogStep.getPossibleAnswers()));
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
