package pl.mmorpg.prototype.client.states.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.dialogs.components.CloseButton;
import pl.mmorpg.prototype.client.states.helpers.Settings;

public class MenuDialog extends CustomDialog
{
	private PlayState linkedState;

	public MenuDialog(PlayState linkedState)
	{
		super("Menu", Settings.DEFAULT_SKIN);
		this.linkedState = linkedState;

		Button closeButton = new CloseButton(this);
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();

		TextButton inventoryButton = new TextButton("Inventory", Settings.DEFAULT_SKIN);

		inventoryButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				linkedState.showOrHideDialog(CustomDialogs.INVENTORY);
			}
		});

		TextButton characterChangeButton = new TextButton("Change character", Settings.DEFAULT_SKIN);

		characterChangeButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				linkedState.userWantsToChangeCharacter();
			}
		});

		TextButton statisticsButton = new TextButton("Statistics", Settings.DEFAULT_SKIN);

		statisticsButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				linkedState.showOrHideDialog(CustomDialogs.STATISTICS);
			}
		});

		TextButton disconnectButton = new TextButton("Disconnect", Settings.DEFAULT_SKIN);

		disconnectButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				linkedState.userWantsToDisconnect();
			}
		});

		getContentTable().add(inventoryButton);
		getContentTable().row();
		getContentTable().add(characterChangeButton);
		getContentTable().row();
		getContentTable().add(statisticsButton);
		getContentTable().row();
		getContentTable().add(disconnectButton);
		getContentTable().row();
		pack();
	}

	@Override
	public CustomDialogs getIdentifier()
	{
		return CustomDialogs.MENU;
	}

}
