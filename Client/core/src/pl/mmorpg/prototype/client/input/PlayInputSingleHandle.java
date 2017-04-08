package pl.mmorpg.prototype.client.input;

import com.badlogic.gdx.Input.Keys;

import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.userinterface.dialogs.QuickAccessDialog;

public class PlayInputSingleHandle extends InputProcessorAdapter
{
	private ActorManipulator dialogManipulator;
	private QuickAccessDialog quickAccesDialog;
	private Player player;

	public PlayInputSingleHandle(ActorManipulator dialogs, Player player)
	{
		this.dialogManipulator = dialogs;
		this.quickAccesDialog = (QuickAccessDialog)dialogs.searchForDialog(QuickAccessDialog.class);
		this.player = player;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		if (dialogManipulator.isMapped(keycode))
			dialogManipulator.showOrHide(keycode);
		else if(keycode >= Keys.F1 && keycode <= Keys.F12)
			quickAccesDialog.useButtonItem(keycode - Keys.F1, player);
				
		return false;
	}

}
