package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.MonsterCreationPacket;

public class MonsterCreationPacketHandler extends PacketHandlerBase<MonsterCreationPacket>
{
	public MonsterCreationPacketHandler(PlayState playState)
	{
	}
	
	@Override
	public void doHandle(MonsterCreationPacket packet)
	{
		System.out.println("Disabled");
//		Function<CollisionMap<GameObject>, GameObject> monsterCreator = 
//				collisionMap -> monstersFactory.produce(packet, collisionMap);
//		playState.add(monsterCreator);
	}

}
