package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Collection;
import java.util.Collections;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.AcceptQuestPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.database.repositories.QuestRepository;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.quests.events.Event;

public class AcceptQuestPacketHandler extends PacketHandlerBase<AcceptQuestPacket>
{
    private GameDataRetriever gameDataRetriever;
    private QuestRepository questRepository = SpringContext.getBean(QuestRepository.class);

    public AcceptQuestPacketHandler(GameDataRetriever gameDataRetriever)
    {
        this.gameDataRetriever = gameDataRetriever;
    }

    @Override
    public Collection<Event> handle(Connection connection, AcceptQuestPacket packet)
    {
        UserCharacter character = gameDataRetriever.getUserCharacterByConnectionId(connection.getID());
        Quest quest = questRepository.findByName(packet.getQuestName());
        CharactersQuests characterQuest = new CharactersQuests(character, quest);
        character.getQuests().add(characterQuest);
        connection.sendTCP(PacketsMaker.makeQuestAcceptedPacket(characterQuest));
        return Collections.emptyList();
    }

}
