package pl.mmorpg.prototype.server.packetshandling;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationReplyPacket;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.helpers.CharacterCreator;
import pl.mmorpg.prototype.server.quests.events.Event;

public class CharacterCreationPacketHandler extends PacketHandlerBase<CharacterCreationPacket>
{
	
	private Map<Integer, User> authenticatedClientsKeyClientId;
	private Server server;

	public CharacterCreationPacketHandler(Map<Integer, User> authenticatedClientsKeyClientId, Server server)
	{
		this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
		this.server = server;
	}
	
	@Override
	public Collection<Event> handle(Connection connection, CharacterCreationPacket packet)
	{
		User user = authenticatedClientsKeyClientId.get(connection.getID());
		CharacterCreationReplyPacket replyPacket = CharacterCreator.tryCreatingCharacter(packet, user.getId());
		server.sendToAllTCP(replyPacket);
        return Collections.emptyList();
	}

}
