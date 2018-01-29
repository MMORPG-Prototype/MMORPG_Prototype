package pl.mmorpg.prototype.client.userinterface.dialogs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import pl.mmorpg.prototype.client.states.ConnectionInfo;
import pl.mmorpg.prototype.client.states.SettingsChoosingState;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.IntegerField;

public class AskForIpDialog extends Dialog
{
	private final TextField ipField;
	private final SettingsChoosingState linkedState;
	private final IntegerField tcpPortField;
	private static final int MAX_PORT_DIGITS = 5;
	private final IntegerField udpPortField;

	public AskForIpDialog(SettingsChoosingState state, ConnectionInfo defaultConnection)
	{
		super("Connect to server", Settings.DEFAULT_SKIN);
		this.linkedState = state;
		ipField = new TextField(defaultConnection.getIp(), getSkin());
		text("Server ip: ");
		this.getContentTable().add(ipField);
		this.getContentTable().row();
		text("Tcp port: ");
		tcpPortField = new IntegerField(defaultConnection.getTcpPort(), getSkin(), MAX_PORT_DIGITS);
		this.getContentTable().add(tcpPortField);
		this.getContentTable().row();
		text("Udp port: ");
		udpPortField = new IntegerField(defaultConnection.getUdpPort(), getSkin(), MAX_PORT_DIGITS);
		this.getContentTable().add(udpPortField);
		this.getContentTable().row();
		
		this.button("Ok", DialogResults.OK);
		this.button("Cancel", DialogResults.CANCEL);
	}
	
	public void simulatePressOkButton()
	{
		result(DialogResults.OK);
	}

	@Override
	protected void result(Object object)
	{
		if (object.equals(DialogResults.OK))
			transferToConnectionState();
		else
			Gdx.app.exit();
	}

	private void transferToConnectionState()
	{
		ConnectionInfo info = new ConnectionInfo(ipField.getText(), tcpPortField.getValue(), udpPortField.getValue());
		linkedState.connect(info);
	}

}
