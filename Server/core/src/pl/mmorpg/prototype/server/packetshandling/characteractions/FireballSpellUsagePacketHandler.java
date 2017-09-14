package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Collection;
import java.util.Collections;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.FireballSpellUsagePacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.spells.Fireball;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.states.PlayState;

public class FireballSpellUsagePacketHandler extends PacketHandlerBase<FireballSpellUsagePacket>
{
    private Server server;
    private PlayState playState;
	private GameDataRetriever gameData;

    public FireballSpellUsagePacketHandler(GameDataRetriever gameData, Server server, PlayState playState)
    {
        this.gameData = gameData;
		this.server = server;
        this.playState = playState;
    }
    

    @Override
    public Collection<Event> handle(Connection connection, FireballSpellUsagePacket packet)
    {
        int characterId = gameData.getCharacterIdByConnectionId(connection.getID());
        PlayerCharacter character = (PlayerCharacter)playState.getObject(characterId);
        if(character.isTargetingAnotherMonster() && character.hasMana(Fireball.MANA_DRAIN))
        {
            Monster targetedMonster = character.getTargetedMonster();
            playState.createFireball(character, targetedMonster);
            character.spellUsed(Fireball.MANA_DRAIN);
            server.sendToTCP(connection.getID(), PacketsMaker.makeManaDrainPacket(Fireball.MANA_DRAIN));
        }
        return Collections.emptyList();
    }

}
