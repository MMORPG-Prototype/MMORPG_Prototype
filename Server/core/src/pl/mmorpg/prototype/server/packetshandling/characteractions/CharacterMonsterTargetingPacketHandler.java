package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterTargetingPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlingHelper;
import pl.mmorpg.prototype.server.states.PlayState;

public class CharacterMonsterTargetingPacketHandler extends PacketHandlerBase<MonsterTargetingPacket>
{
    private PlayState playState;
    private Map<Integer, UserInfo> loggedUsersKeyUserId;
    private Map<Integer, User> authenticatedClientsKeyClientId;

    public CharacterMonsterTargetingPacketHandler(PlayState playState, Map<Integer, UserInfo> loggedUsersKeyUserId,
            Map<Integer, User> authenticatedClientsKeyClientId)
    {
        this.playState = playState;
        this.loggedUsersKeyUserId = loggedUsersKeyUserId;
        this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
    }

    @Override
    public void handle(Connection connection, MonsterTargetingPacket packet)
    {
        GameObject target = playState.getCollisionMap().get(packet.gameX, packet.gameY);
        if (target != null && target instanceof Monster)
        {
            UserCharacter userCharacter = PacketHandlingHelper.getUserCharacterByConnectionId(connection.getID(),
                    loggedUsersKeyUserId, authenticatedClientsKeyClientId);
            GameObject source = playState.getObject(userCharacter.getId());
            playState.objectTargeted((Monster) source, (Monster) target);
            connection.sendTCP(PacketsMaker.makeTargetingReplyPacket(target));
        }
    }

}
