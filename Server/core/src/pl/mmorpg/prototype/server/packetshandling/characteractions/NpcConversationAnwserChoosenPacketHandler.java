package pl.mmorpg.prototype.server.packetshandling;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.NpcConversationAnwserChoosenPacket;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.npcs.Npc;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.quests.events.NpcDialogEvent;
import pl.mmorpg.prototype.server.states.PlayState;

public class NpcConversationAnwserChoosenPacketHandler extends PacketHandlerBase<NpcConversationAnwserChoosenPacket>
{
	private GameDataRetriever gameDataRetriever;
	private PlayState playState;

	public NpcConversationAnwserChoosenPacketHandler(PlayState playState, GameDataRetriever gameDataRetriever)
	{
		this.playState = playState;
		this.gameDataRetriever = gameDataRetriever;
	}

	@Override
	public void handle(Connection connection, NpcConversationAnwserChoosenPacket packet)
	{
		PlayerCharacter player = (PlayerCharacter) playState.getObject(gameDataRetriever.getCharacterIdByConnectionId(connection.getID()));
		Npc npc = (Npc)playState.getObject(packet.getNpcId());
		Event npcDialogEvent = new NpcDialogEvent(packet.getAnwser(), npc, (PacketsSender)playState);
		npcDialogEvent.addReceiver(player);
		playState.enqueueEvent(npcDialogEvent);
	}

}
