package pl.mmorpg.prototype.client.states.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.states.PlayState;

public class DisconnectDialog extends Dialog
{
	private PlayState linkedState;

	public DisconnectDialog(PlayState linkedState)
	{
		super("Disconnect?", Settings.DEFAULT_SKIN);
		this.linkedState = linkedState;
		button("Disconnect", DialogResults.DISCONNECT);
	}

	/*@Override
	protected void result(Object object)
	{
		linkedState.userWantsToDisconnect();
	}*/

}
