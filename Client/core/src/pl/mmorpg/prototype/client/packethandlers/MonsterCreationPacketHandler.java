package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.objects.monsters.Monster;
import pl.mmorpg.prototype.client.objects.monsters.MonstersFactory;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.MonsterCreationPacket;

public class MonsterCreationPacketHandler extends PacketHandlerBase<MonsterCreationPacket>
{
	private PlayState playState;
	private static MonstersFactory monstersFactory = new MonstersFactory();

	public MonsterCreationPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handlePacket(MonsterCreationPacket packet)
	{
		Monster newObject = monstersFactory.produce(packet);
		playState.add(newObject);
	}

}
