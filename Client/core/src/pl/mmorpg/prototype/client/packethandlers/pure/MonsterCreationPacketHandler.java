package pl.mmorpg.prototype.client.packethandlers.pure;

import java.util.function.Function;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.monsters.MonstersFactory;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.MonsterCreationPacket;

public class MonsterCreationPacketHandler extends PacketHandlerBase<MonsterCreationPacket>
{
	private PlayState playState;
	private static final MonstersFactory monstersFactory = new MonstersFactory();

	public MonsterCreationPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(MonsterCreationPacket packet)
	{
		Function<CollisionMap<GameObject>, GameObject> monsterCreator = 
				collisionMap -> monstersFactory.produce(packet, collisionMap);
		playState.add(monsterCreator);
	}

}
