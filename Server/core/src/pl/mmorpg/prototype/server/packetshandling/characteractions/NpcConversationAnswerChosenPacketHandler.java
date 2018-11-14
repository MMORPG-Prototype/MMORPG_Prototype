package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.NpcConversationAnswerChosenPacket;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.npcs.Npc;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.quests.events.NpcDialogEvent;
import pl.mmorpg.prototype.server.states.PlayState;

public class NpcConversationAnswerChosenPacketHandler extends PacketHandlerBase<NpcConversationAnswerChosenPacket>
{
	private GameDataRetriever gameDataRetriever;
	private PlayState playState;

	public NpcConversationAnswerChosenPacketHandler(PlayState playState, GameDataRetriever gameDataRetriever)
	{
		this.playState = playState;
		this.gameDataRetriever = gameDataRetriever;
	}

	@Override
	public void handle(Connection connection, NpcConversationAnswerChosenPacket packet)
	{
		PlayerCharacter player = (PlayerCharacter) playState.getObject(gameDataRetriever.getCharacterIdByConnectionId(connection.getID()));
		Npc npc = (Npc)playState.getObject(packet.getNpcId());
		Event npcDialogEvent = new NpcDialogEvent(packet.getAnswer(), npc);
		npcDialogEvent.addReceiver(player);
		playState.enqueueQuestEvent(npcDialogEvent);
	}

}
