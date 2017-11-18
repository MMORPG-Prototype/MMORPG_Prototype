package pl.mmorpg.prototype.client.input;

import com.badlogic.gdx.Input.Keys;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.QuickAccessDialog;

public class PlayInputSingleHandle extends InputProcessorAdapter
{
    private UserInterface userInterface;
    private QuickAccessDialog quickAccessDialog;
    private Player player;
    private PacketsSender packetSender;

    public PlayInputSingleHandle(UserInterface userInterface, Player player, PacketsSender packetSender)
    {
        this.userInterface = userInterface;
        this.packetSender = packetSender;
        this.quickAccessDialog = (QuickAccessDialog) userInterface.getDialogs().searchForDialog(QuickAccessDialog.class);
        this.player = player;
    }
    
    @Override
    protected boolean shouldAvoidInput()
    {
    	return userInterface.isTextFieldFocused();
    }

    @Override
    public boolean keyDown(int keycode)
    {   
    	if(shouldAvoidInput())
    		return false;
    	
    	ActorManipulator dialogs = userInterface.getDialogs();
        if (dialogs.isMapped(keycode))
            dialogs.showOrHide(keycode);
        else if (keycode == Keys.NUM_1)
            packetSender.send(PacketsMaker.makeFireballSpellUsagePacket());
        else if (keycode >= Keys.F1 && keycode <= Keys.F12)
            quickAccessAction(keycode);

        return false;
    }
    
    @Override
    public boolean keyUp(int keycode)
    {
    	return false;
    }

	private void quickAccessAction(int keycode)
	{
		int cellPosition = keycode - Keys.F1;
		if(quickAccessDialog.isFieldTaken(cellPosition))
			quickAccessDialog.useButtonItem(cellPosition, player, packetSender);
	}

}
