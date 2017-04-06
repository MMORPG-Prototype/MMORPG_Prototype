package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.GameObjectTargetPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;

public class ObjectRepositionPacketHandler extends PacketHandlerBase<ObjectRepositionPacket>
{
	private PlayState playState;

	public ObjectRepositionPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handlePacket(ObjectRepositionPacket packet)
	{
		GameObject operationTarget = playState.getObject(packet.id);
		operationTarget.setX(packet.x);
		operationTarget.setY(packet.y);	
	}
	
	@Override
	public boolean canHandle(ObjectRepositionPacket packet)
	{
		return objectExist(packet);
	}
	
	private boolean objectExist(GameObjectTargetPacket packet)
	{
		return playState.getObject(packet.id) != null;
	}

}
