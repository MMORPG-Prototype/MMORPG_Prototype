package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;

public class MenuDialog extends Dialog
{
	private UserInterface linkedInterface;

	public MenuDialog(UserInterface linkedInterface)
	{
		super("Menu", Settings.DEFAULT_SKIN);
		this.linkedInterface = linkedInterface;

		Button closeButton = new CloseButton(this);
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();

		TextButton inventoryButton = createButton("Inventory",
				() -> linkedInterface.showOrHideDialog(InventoryDialog.class));
		TextButton characterChangeButton = createButton("Change character",
				() -> linkedInterface.userWantsToChangeCharacter());
		TextButton statisticsButton = createButton("Statistics",
				() -> linkedInterface.showOrHideDialog(StatisticsDialog.class));
		TextButton disconnectButton = createButton("Disconnect", () -> linkedInterface.userWantsToDisconnect());

		getContentTable().add(inventoryButton);
		getContentTable().row();
		getContentTable().add(characterChangeButton);
		getContentTable().row();
		getContentTable().add(statisticsButton);
		getContentTable().row();
		getContentTable().add(disconnectButton);
		getContentTable().row();

		centerPosition();
		pack();
	}

	private TextButton createButton(String label, Runnable onClickAction)
	{
		TextButton inventoryButton = new TextButton(label, Settings.DEFAULT_SKIN);

		inventoryButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				onClickAction.run();
			}
		});
		return inventoryButton;
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		if(visible)
			centerPosition();
		super.setVisible(visible);
	}

	private void centerPosition()
	{
		setPosition(Math.round((Gdx.graphics.getWidth() - getWidth()) / 2),
				Math.round((Gdx.graphics.getHeight() - getHeight()) / 2));	
	}

}
