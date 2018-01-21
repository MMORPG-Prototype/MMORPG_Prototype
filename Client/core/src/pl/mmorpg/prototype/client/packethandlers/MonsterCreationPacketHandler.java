package pl.mmorpg.prototype.client.packethandlers;

import java.util.function.Function;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.monsters.MonstersFactory;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.MonsterCreationPacket;

public class MonsterCreationPacketHandler extends PacketHandlerAdapter<MonsterCreationPacket>
{
	private PlayState playState;
	private static final MonstersFactory monstersFactory = new MonstersFactory();

	public MonsterCreationPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(MonsterCreationPacket packet)
	{
		Function<CollisionMap<GameObject>, GameObject> monsterCreator = 
				collisionMap -> monstersFactory.produce(packet, collisionMap);
		playState.add(monsterCreator);
	}

}
