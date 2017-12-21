package pl.mmorpg.prototype.client.packethandlers;

import java.util.function.Function;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.objects.monsters.Monster;
import pl.mmorpg.prototype.client.objects.monsters.MonstersFactory;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.PlayerCreationPacket;

public class PlayerCreationPacketHandler extends PacketHandlerBase<PlayerCreationPacket>
{
	private PlayState playState;

	public PlayerCreationPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handlePacket(PlayerCreationPacket packet)
	{
		Function<CollisionMap<GameObject>, GameObject> playerCreator = 
				collisionMap -> createPlayer(packet, collisionMap);
		playState.add(playerCreator);
	}

	private Monster createPlayer(PlayerCreationPacket packet, CollisionMap<GameObject> collisionMap)
	{
		Player player = (Player)new MonstersFactory().produce(packet, collisionMap);
		player.initialize(packet.data);
		return player;
	}

}
