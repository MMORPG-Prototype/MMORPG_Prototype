package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Collection;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.RetrieveGoldRewardPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;

public class RetrieveGoldRewardPacketHandler extends PacketHandlerBase<RetrieveGoldRewardPacket>
{
    private GameDataRetriever gameDataRetriever;
    private PacketsSender packetsSender;

    public RetrieveGoldRewardPacketHandler(GameDataRetriever gameDataRetriever, PacketsSender packetsSender)
    {
        this.gameDataRetriever = gameDataRetriever;
        this.packetsSender = packetsSender;
    }

    @Override
    public void handle(Connection connection, RetrieveGoldRewardPacket packet)
    {
        UserCharacter character = gameDataRetriever.getUserCharacterByConnectionId(connection.getID());
        CharactersQuests quest = findSuiteQuest(character.getQuests(), packet.getQuestName());
        int goldReward = quest.getGoldReward();
        if (goldReward > 0)
        {
            quest.setGoldReward(0);
            character.setGold(character.getGold() + goldReward);
            packetsSender.sendTo(connection.getID(), PacketsMaker.makeGoldReceivePacket(goldReward));
            packetsSender.sendTo(connection.getID(),
                    PacketsMaker.makeQuestRewardGoldRemovalPacket(goldReward, packet.getRetrieveItemDialogId()));
        } else
            packetsSender.sendTo(connection.getID(),
                    PacketsMaker.makeUnacceptableOperationPacket("There is no gold left!"));

    }

    private CharactersQuests findSuiteQuest(Collection<CharactersQuests> quests, String questName)
    {
        return quests.stream().filter(characterQuest -> characterQuest.getQuest().getName().equals(questName)).findAny()
                .get();
    }

}
