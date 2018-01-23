package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.PlayerCreationPacket;

public class PlayerCreationPacketHandler extends PacketHandlerBase<PlayerCreationPacket>
{

	public PlayerCreationPacketHandler(PlayState playState)
	{
	}
	
	@Override
	public void doHandle(PlayerCreationPacket packet)
	{
		System.out.println("Player creation handler disabled");
//		Function<CollisionMap<GameObject>, GameObject> playerCreator = 
//				collisionMap -> createPlayer(packet, collisionMap);
//		playState.add(playerCreator);
	}

//	private Monster createPlayer(PlayerCreationPacket packet, CollisionMap<GameObject> collisionMap)
//	{
//		Player player = (Player)new MonstersFactory().produce(packet, collisionMap);
//		player.initialize(packet.data);
//		return player;
//	}

}
