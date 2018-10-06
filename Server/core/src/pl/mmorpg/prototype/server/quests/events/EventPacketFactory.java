package pl.mmorpg.prototype.server.quests.events;

import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.exceptions.NoSuchEventPacketMakerException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EventPacketFactory
{
	private final Map<Class<? extends Event>, EventPacketMaker> packetMakers = createPacketMakers();

	private Map<Class<? extends Event>, EventPacketMaker> createPacketMakers()
	{
		Map<Class<? extends Event>, EventPacketMaker> packetMakers = new HashMap<>();
		packetMakers.put(AcceptQuestEvent.class, PacketsMaker::makeAcceptQuestEventPacket);
		packetMakers.put(MonsterKilledEvent.class, PacketsMaker::makeMonsterKilledEvent);
		packetMakers.put(NpcDialogEvent.class, PacketsMaker::makeNpcDialogEventPacket);
		packetMakers.put(NpcDialogStartEvent.class, PacketsMaker::makeNpcDialogStartEventPacket);
		return packetMakers;
	}

	public Object makeEventPacket(Event e)
	{
		EventPacketMaker packetMaker = packetMakers.get(e.getClass());
		return Optional.ofNullable(packetMaker)
				.map(p -> p.makeEventPacket(e))
				.orElseThrow(() -> new NoSuchEventPacketMakerException(e.getClass()));
	}
}
