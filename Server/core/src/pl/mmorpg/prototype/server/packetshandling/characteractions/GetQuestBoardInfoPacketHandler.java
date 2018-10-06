package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.quest.QuestBoardInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.GetQuestBoardInfoPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.objects.ineractivestaticobjects.QuestBoard;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class GetQuestBoardInfoPacketHandler extends PacketHandlerBase<GetQuestBoardInfoPacket>
{
	private final PlayState playState;
	private final GameDataRetriever gameData;

	public GetQuestBoardInfoPacketHandler(PlayState playState, GameDataRetriever gameData)
	{
		this.playState = playState;
		this.gameData = gameData;
	}
	
	@Override
	public void handle(Connection connection, GetQuestBoardInfoPacket packet)
	{
		QuestBoard questBoard = (QuestBoard) playState.getObject(packet.getQuestBoardId());
		QuestBoardInfoPacket questBoardInfoPacket = makeQuestBoardInfoPacket(connection.getID(), questBoard);
		connection.sendTCP(questBoardInfoPacket);
	}
	
	private QuestBoardInfoPacket makeQuestBoardInfoPacket(int connectionId, QuestBoard questBoard)
	{
		Collection<CharactersQuests> characterQuests = gameData.getCharacterQuestsByConnectionId(connectionId);
		Collection<Quest> quests = characterQuests.stream()
				.map(CharactersQuests::getQuest)
				.collect(Collectors.toList());
		Predicate<Quest> shouldQuestBeIncluded = quest -> !quests.contains(quest);
		return PacketsMaker.makeQuestBoardInfoPacket(questBoard, shouldQuestBeIncluded);
	}
}
