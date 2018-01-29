package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class ErrorInfoDialog extends Dialog
{	
	private Runnable onOkButton;

	public ErrorInfoDialog(String message, Runnable onOkButton)
	{
		super("Error", Settings.DEFAULT_SKIN);
		this.onOkButton = onOkButton;
		Label errorLabel = new Label(message, getSkin());
		errorLabel.setColor(Color.RED);
		
		this.text(errorLabel);
		this.button("Ok", DialogResults.OK);
	}
	
	@Override
	protected void result(Object result)
	{
		if(result.equals(DialogResults.OK))
			onOkButton.run();
	}
}
