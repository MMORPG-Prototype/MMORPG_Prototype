package pl.mmorpg.prototype.server.packetshandling;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessageReplyPacket;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.quests.events.Event;

public class ChatMessagePacketHandler extends PacketHandlerBase<ChatMessagePacket>
{
	private Server server;
	private GameDataRetriever gameData;

	public ChatMessagePacketHandler(Server server, GameDataRetriever gameData)
	{
		this.server = server;
		this.gameData = gameData;
	}

	@Override
	public Collection<Event> handle(Connection connection, ChatMessagePacket packet)
	{
		Field connectionsField = FieldUtils.getField(Server.class, "connections", true);
		
		Connection[] connections;
		try
		{
			connections = (Connection[])FieldUtils.readField(connectionsField, server, true);
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		ChatMessageReplyPacket newPacket = new ChatMessageReplyPacket();
		UserCharacter sender = gameData.getUserCharacterByConnectionId(connection.getID());
		String nickname = sender.getNickname();
		newPacket.setMessage(packet.getMessage());
		newPacket.setNickname(nickname);
		UserCharacter character;
		for(Connection client : connections)		
			if((character = gameData.getUserCharacterByConnectionId(client.getID())) != null)
			{
				newPacket.setSourceCharacterId(character.getId());
				server.sendToTCP(client.getID(), newPacket);
			}
        return Collections.emptyList();
	}


}
