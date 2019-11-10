package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ButtonCreator;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;

public class MenuDialog extends Dialog
{

	public MenuDialog(UserInterface linkedInterface)
	{
		super("Menu", Settings.DEFAULT_SKIN);

		Button closeButton = new CloseButton(this);
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();

		TextButton characterChangeButton = ButtonCreator.createTextButton("Change character",
				linkedInterface::userWantsToChangeCharacter);
		TextButton logOutButton = ButtonCreator.createTextButton("Log out", linkedInterface::userWantsToLogOut);
		TextButton disconnectButton = ButtonCreator.createTextButton("Disconnect",
				linkedInterface::userWantsToDisconnect);
		TextButton exitButton = ButtonCreator.createTextButton("Exit", () -> Gdx.app.exit());

		getContentTable().add(characterChangeButton);
		getContentTable().row();
		getContentTable().add(logOutButton);
		getContentTable().row();
		getContentTable().add(disconnectButton);
		getContentTable().row();
		getContentTable().add(exitButton);
		getContentTable().row();

		DialogUtils.centerPosition(this);
		pack();
	}

	
	@Override
	public void setVisible(boolean visible)
	{
		if(visible)
			DialogUtils.centerPosition(this);
		super.setVisible(visible);
	}


}
