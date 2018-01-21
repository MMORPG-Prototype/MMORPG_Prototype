package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.QuestBoardInfoPacket;

public class QuestBoardInfoPacketHandler extends PacketHandlerBase<QuestBoardInfoPacket>
{
	private PlayState playState;

	public QuestBoardInfoPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(QuestBoardInfoPacket packet)
	{
		playState.questBoardInfoPacketReceived(packet);
	}

}
