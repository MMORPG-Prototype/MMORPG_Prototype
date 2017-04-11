package pl.mmorpg.prototype.client.packethandlers;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.FrameworkMessage;

import pl.mmorpg.prototype.client.exceptions.UnknownPacketTypeException;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessageReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.HpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.MonsterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.MpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.PlayerCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ExperienceGainPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterTargetingReplyPacket;

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
		packetHandlers.put(ChatMessageReplyPacket.class, new ChatMessageReplyPacketHandler(playState));
		packetHandlers.put(MonsterTargetingReplyPacket.class,
				new CharacterMonsterTargetingReplyPacketHandler(playState));
		packetHandlers.put(MonsterDamagePacket.class, new MonsterDamagePacketHandler(playState));
		packetHandlers.put(MonsterCreationPacket.class, new MonsterCreationPacketHandler(playState));
		packetHandlers.put(ExperienceGainPacket.class, new ExperienceGainPacketHandler(playState));
		packetHandlers.put(PlayerCreationPacket.class, new PlayerCreationPacketHandler(playState));
		packetHandlers.put(HpChangeByItemUsagePacket.class, new HpChangeByItemUsagePacketHandler(playState));
		packetHandlers.put(MpChangeByItemUsagePacket.class, new MpChangeByItemUsagePacketHandler(playState));

		// Ignore frameowrk keepAliveMessage
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
