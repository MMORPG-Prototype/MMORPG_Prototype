package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.GameObjectTargetPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class MovePacketHandler extends PacketHandlerBase<GameObjectTargetPacket> implements MoveAction
{
	private PlayState playState;
	private Server server;
	private MoveAction moveAction;

	public MovePacketHandler(Server server, PlayState playState)
	{
		this.server = server;
		this.playState = playState;
	}
	
	@Override
	public void handle(Connection connection, GameObjectTargetPacket packet)
	{
		MovableGameObject operationTarget = (MovableGameObject) playState.getObject(packet.id);
		this.perform(operationTarget, playState.getCollisionMap());
		server.sendToAllTCP(
				PacketsMaker.makeRepositionPacket(packet.id, operationTarget.getX(), operationTarget.getY()));	
	}
	
	
	

}
