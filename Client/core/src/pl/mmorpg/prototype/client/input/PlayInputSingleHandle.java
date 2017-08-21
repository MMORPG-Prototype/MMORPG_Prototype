package pl.mmorpg.prototype.client.input;

import com.badlogic.gdx.Input.Keys;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
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
        this.quickAccesDialog = (QuickAccessDialog) dialogs.searchForDialog(QuickAccessDialog.class);
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if (dialogs.isMapped(keycode) && !dialogs.searchForDialog(ChatDialog.class).isVisible())
            dialogs.showOrHide(keycode);
        else if (keycode == Keys.NUM_1)
            packetSender.send(PacketsMaker.makeFireballSpellUsagePacket());
        else if (keycode >= Keys.F1 && keycode <= Keys.F12)
            quickAccessAction(keycode);

        return false;
    }

	private void quickAccessAction(int keycode)
	{
		int cellPosition = keycode - Keys.F1;
		if(quickAccesDialog.isFieldTaken(cellPosition))
			quickAccesDialog.useButtonItem(cellPosition, player, packetSender);
	}

}
