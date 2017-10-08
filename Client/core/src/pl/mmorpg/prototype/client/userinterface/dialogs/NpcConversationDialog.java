package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.objects.monsters.npcs.Npc;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.LineBreaker;

public class NpcConversationDialog extends AutoCleanupOnCloseButtonDialog
{

	public NpcConversationDialog(Npc npc, String speech, String[] possibleAnwsers, PacketsSender packetsSender,
			ActorManipulator linkedManipulator)
	{
		super(npc.getName(), linkedManipulator, npc.getId());
		
		LineBreaker lineBreaker = new LineBreaker(speech, 30);
		getContentTable().add(lineBreaker);
		getContentTable().row();
		for(String anwser : possibleAnwsers)
		{
			Label label = new Label(anwser, getSkin());
			getContentTable().add(label);
			getContentTable().row();
		}
		this.pack();
		setPosition(0, 100);
	}

}
