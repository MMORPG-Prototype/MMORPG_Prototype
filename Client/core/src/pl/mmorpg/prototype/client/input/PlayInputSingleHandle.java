package pl.mmorpg.prototype.client.input;

import com.badlogic.gdx.Input.Keys;

import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.ItemQuickAccessDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.SpellQuickAccessDialog;

public class PlayInputSingleHandle extends InputProcessorAdapter
{
    private UserInterface userInterface;
    private ItemQuickAccessDialog itemQuickAccessDialog;
    private SpellQuickAccessDialog spellQuickAccessDialog;
    private Player player;
    private PacketsSender packetSender;

    public PlayInputSingleHandle(UserInterface userInterface, Player player, PacketsSender packetSender)
    {
        this.userInterface = userInterface;
        this.packetSender = packetSender;
        this.itemQuickAccessDialog = userInterface.getDialogs().searchForDialog(ItemQuickAccessDialog.class);
        this.spellQuickAccessDialog = userInterface.getDialogs().searchForDialog(SpellQuickAccessDialog.class);
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
        else if (keycode >= Keys.NUM_1 && keycode <= Keys.NUM_9)
        	spellQuickAccessAction(keycode - Keys.NUM_1);
        else if (keycode == Keys.MINUS)
            spellQuickAccessAction(10);
        else if (keycode == Keys.EQUALS)
            spellQuickAccessAction(11);
        else if (keycode >= Keys.F1 && keycode <= Keys.F12)
            itemQuickAccessAction(keycode - Keys.F1);

        return false;
    }
    
    private void spellQuickAccessAction(int cellPosition)
	{
		if(spellQuickAccessDialog.isFieldTaken(cellPosition))
			spellQuickAccessDialog.useButtonSpell(cellPosition, packetSender);
	}

	private void itemQuickAccessAction(int cellPosition)
	{
		if(itemQuickAccessDialog.isFieldTaken(cellPosition))
			itemQuickAccessDialog.useButtonItem(cellPosition, player, packetSender);
	}
	
	@Override
    public boolean keyUp(int keycode)
    {
    	return false;
    }
}
