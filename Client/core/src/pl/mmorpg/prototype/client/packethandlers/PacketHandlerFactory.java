package pl.mmorpg.prototype.client.packethandlers;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.FrameworkMessage;

import pl.mmorpg.prototype.client.exceptions.UnknownPacketTypeException;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;

public class PacketHandlerFactory
{
	private Map<Class<?>, PacketHandler> packetHandlers = new HashMap<>();

	public PacketHandlerFactory(final PlayState playState, final StateManager states)
	{
		packetHandlers.put(AuthenticationReplyPacket.class, new AuthenticationReplyPacketHandler(states));
		packetHandlers.put(CharacterCreationReplyPacket.class, new CharacterCreationReplyPacketHandler(states));
		packetHandlers.put(CharacterItemDataPacket.class, new CharacterItemDataPacketHandler(playState));
		packetHandlers.put(ObjectCreationPacket.class, new ObjectCreationPacketHandler(playState));
		packetHandlers.put(ObjectRemovePacket.class, new ObjectRemovePacketHandler(playState));
		packetHandlers.put(ObjectRepositionPacket.class, new ObjectRepositionPacketHandler(playState));
		packetHandlers.put(RegisterationReplyPacket.class, new RegisterationReplyPacketHandler(states));
		packetHandlers.put(UserCharacterDataPacket[].class, new UserCharacterDataArrayPacketHandler(states));
		packetHandlers.put(ChatMessagePacket.class, new ChatMessagePacketHandler(playState));
		
		//Ignore frameowrk keepAliveMessage
		packetHandlers.put(FrameworkMessage.KeepAlive.class, new NullPacketHandler());
	}

	public PacketHandler produce(Object object)
	{
		PacketHandler packetHandler = packetHandlers.get(object.getClass());
		if (packetHandler == null)
			throw new UnknownPacketTypeException(object);
		return packetHandler;
	}
}
