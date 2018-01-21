package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
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
	public void doHandle(ObjectRepositionPacket packet)
	{
		GameObject operationTarget = playState.getObject(packet.id);
		operationTarget.setX(packet.x);
		operationTarget.setY(packet.y);	
	}
	
	@Override
	public boolean canBeHandled(ObjectRepositionPacket packet)
	{
		return objectExist(packet);
	}
	
	private boolean objectExist(GameObjectTargetPacket packet)
	{
		return playState.getObject(packet.id) != null;
	}

	@Override
	public boolean canBeOmmited(ObjectRepositionPacket packet)
	{
		return true;
	}
}
