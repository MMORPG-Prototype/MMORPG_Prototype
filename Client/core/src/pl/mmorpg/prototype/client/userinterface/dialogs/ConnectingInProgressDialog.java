package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class ConnectingInProgressDialog extends Dialog
{
	private static final String messagePrefix = "Connecting";
	private static final float messageDotsUpdateInterval = 0.5f;
	private String messageSuffix = "";
	private final Label messageLabel = new Label(messagePrefix + "   ", getSkin());
	private float currentMessageDotsUpdateTime = 0.0f;

	public ConnectingInProgressDialog()
	{
		this(Settings.DEFAULT_SKIN);
	}

	public ConnectingInProgressDialog(Skin skin)
	{
		super("", skin);
		text(messageLabel);
		messageLabel.setAlignment(Align.left);
	}
	
	@Override
	public void act(float detlaTime)
	{
		currentMessageDotsUpdateTime += detlaTime;
		if (currentMessageDotsUpdateTime >= messageDotsUpdateInterval)
		{
			currentMessageDotsUpdateTime -= messageDotsUpdateInterval;
			updateMessageSuffix();
		}
	}

	private void updateMessageSuffix()
	{
		if(messageSuffix.equals("..."))
			messageSuffix = "";
		else
			messageSuffix += ".";
		messageLabel.setText(messagePrefix + messageSuffix);
	}
}
