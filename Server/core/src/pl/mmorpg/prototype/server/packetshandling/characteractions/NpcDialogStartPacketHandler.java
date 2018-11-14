package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.NpcDialogStartRequestPacket;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.npcs.QuestDialogNpc;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.quests.events.NpcDialogEvent;
import pl.mmorpg.prototype.server.quests.events.NpcDialogStartEvent;
import pl.mmorpg.prototype.server.states.PlayState;

public class NpcDialogStartPacketHandler extends PacketHandlerBase<NpcDialogStartRequestPacket>
{
	private PlayState playState;
	private GameDataRetriever gameDataRetriever;

	public NpcDialogStartPacketHandler(PlayState playState, GameDataRetriever gameDataRetriever)
	{
		this.playState = playState;
		this.gameDataRetriever = gameDataRetriever;
	}
	
	@Override
	public void handle(Connection connection, NpcDialogStartRequestPacket packet)
	{
		QuestDialogNpc npc = (QuestDialogNpc) playState.getObject(packet.getNpcId());
		PlayerCharacter player = (PlayerCharacter) playState
				.getObject(gameDataRetriever.getCharacterIdByConnectionId(connection.getID()));
		propagateQuestDialogEvent(npc, player);
	}
	
	private void propagateQuestDialogEvent(QuestDialogNpc npc, PlayerCharacter player)
	{
		Event talkWithNpcEvent = new NpcDialogStartEvent(npc);
		talkWithNpcEvent.addReceiver(player);
		playState.enqueueQuestEvent(talkWithNpcEvent);
	}

}
