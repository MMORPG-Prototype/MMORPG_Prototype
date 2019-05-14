package pl.mmorpg.prototype.client.userinterface.dialogs.components.chat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import pl.mmorpg.prototype.clientservercommon.packets.ChatMessageReplyPacket;

public class ChatPane extends ScrollPane
{
	private final ChatList chatList;
	private final Runnable scrollingTask = () -> setScrollPercentY(1.0f);

	public ChatPane()
	{
		super(new ChatList());
		chatList = (ChatList) getActor();
		setSmoothScrolling(false);  
		setTransform(true); 
	}

	public void addMessage(ChatMessageReplyPacket packet)
	{
		chatList.addMessage(packet.getNickname(), packet.getMessage());
		Gdx.app.postRunnable(scrollingTask);
	}

}
