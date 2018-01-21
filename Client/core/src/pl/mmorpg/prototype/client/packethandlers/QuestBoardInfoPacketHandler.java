package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.QuestBoardInfoPacket;

public class QuestBoardInfoPacketHandler extends PacketHandlerAdapter<QuestBoardInfoPacket>
{
	private PlayState playState;

	public QuestBoardInfoPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(QuestBoardInfoPacket packet)
	{
		playState.questBoardInfoPacketReceived(packet);
	}

}
