package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;

public class ChatPane extends ScrollPane
{
	private final ChatList chatList;

	public ChatPane()
	{
		super(new ChatList());
		chatList = (ChatList) getWidget();
		setSmoothScrolling(false);
		setTransform(true);
	}

	public void addMessage(ChatMessagePacket packet)
	{
		chatList.addMessage(packet.getNickname(), packet.getMessage());
		setScrollPercentY(110.0f);
	}

}
