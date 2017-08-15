package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.GameObjectTargetPacket;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class MovePacketHandler extends PacketHandlerBase<GameObjectTargetPacket> implements MoveAction<GameObject>
{
	private PlayState playState;

	public MovePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(Connection connection, GameObjectTargetPacket packet)
	{
		MovableGameObject operationTarget = (MovableGameObject) playState.getObject(packet.id);
		this.perform(operationTarget, playState.getCollisionMap());	
	}
	
	
	

}
