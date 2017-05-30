package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.FireballSpellUsagePacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.spells.Fireball;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlingHelper;
import pl.mmorpg.prototype.server.states.PlayState;

public class FireballSpellUsagePacketHandler extends PacketHandlerBase<FireballSpellUsagePacket>
{
    private Map<Integer, UserInfo> loggedUsersKeyUserId;
    private Map<Integer, User> authenticatedClientsKeyClientId;
    private Server server;
    private PlayState playState;


    public FireballSpellUsagePacketHandler(Map<Integer, UserInfo> loggedUsersKeyUserId,
            Map<Integer, User> authenticatedClientsKeyClientId, Server server, PlayState playState)
    {
        this.loggedUsersKeyUserId = loggedUsersKeyUserId;
        this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
        this.server = server;
        this.playState = playState;
    }
    

    @Override
    public void handle(Connection connection, FireballSpellUsagePacket packet)
    {
        int characterId = PacketHandlingHelper.getCharacterIdByConnectionId(connection.getID(), loggedUsersKeyUserId, authenticatedClientsKeyClientId);
        PlayerCharacter character = (PlayerCharacter)playState.getObject(characterId);
        if(character.isTargetingAnotherMonster() && character.hasMana(Fireball.MANA_DRAIN))
        {
            Monster targetedMonster = character.getTargetedMonster();
            playState.createFireball(character, targetedMonster);
            character.spellUsed(Fireball.MANA_DRAIN);
            server.sendToTCP(connection.getID(), PacketsMaker.makeManaDrainPacket(Fireball.MANA_DRAIN));
        }
    }

}
