package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.objects.monsters.npcs.Npc;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.LineBreaker;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.MouseHoverHighlightingClickableLabel;

public class NpcConversationDialog extends AutoCleanupOnCloseButtonDialog
{
	private PacketsSender packetsSender;
	private Npc npc;

	public NpcConversationDialog(Npc npc, String speech, String[] possibleAnswers, PacketsSender packetsSender,
			ActorManipulator linkedManipulator)
	{
		super(npc.getName(), linkedManipulator, npc.getId());
		this.npc = npc;
		this.packetsSender = packetsSender;

		update(speech, possibleAnswers);
		setPosition(0, 100);
	}

	public void update(String speech, String[] possibleAnswers)
	{
		getContentTable().clear();
		LineBreaker lineBreaker = new LineBreaker(speech, 30);
		getContentTable().add(lineBreaker);
		getContentTable().row();
		for (String answer : possibleAnswers)
		{
			Runnable onClickAction = () -> packetsSender
					.send(PacketsMaker.makeNpcConversationAnwserChoosenPacket(npc.getId(), answer));
			Label label = new MouseHoverHighlightingClickableLabel(answer, onClickAction);
			getContentTable().add(label);
			getContentTable().row();
		}
		this.pack();
	}

}
