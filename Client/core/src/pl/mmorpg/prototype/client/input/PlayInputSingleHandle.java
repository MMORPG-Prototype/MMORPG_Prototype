package pl.mmorpg.prototype.client.input;

import com.badlogic.gdx.Input.Keys;

import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.userinterface.dialogs.ChatDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.QuickAccessDialog;

public class PlayInputSingleHandle extends InputProcessorAdapter
{
	private ActorManipulator dialogs;
	private QuickAccessDialog quickAccesDialog;
	private Player player;
	private PacketsSender packetSender;

	public PlayInputSingleHandle(ActorManipulator dialogs, Player player, PacketsSender packetSender)
	{
		this.dialogs = dialogs;
		this.packetSender = packetSender;
		this.quickAccesDialog = (QuickAccessDialog)dialogs.searchForDialog(QuickAccessDialog.class);
		this.player = player;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		if (dialogs.isMapped(keycode) && !dialogs.searchForDialog(ChatDialog.class).isVisible())
			dialogs.showOrHide(keycode);
		else if(keycode >= Keys.F1 && keycode <= Keys.F12)
			quickAccesDialog.useButtonItem(keycode - Keys.F1, player, packetSender);
				
		return false;
	}

}
