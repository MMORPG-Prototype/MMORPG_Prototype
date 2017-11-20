package pl.mmorpg.prototype.server.packetshandling;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.LogoutPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.CharacterDatabaseSaver;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.states.PlayState;

public class LogoutPacketHandler extends PacketHandlerBase<LogoutPacket>
{
    private Map<Integer, UserInfo> loggedUsersKeyUserId;
    private Map<Integer, User> authenticatedClientsKeyClientId;
    private PlayState playState;
    private Server server;

    public LogoutPacketHandler(Map<Integer, UserInfo> loggedUsersKeyUserId,
            Map<Integer, User> authenticatedClientsKeyClientId, Server server, PlayState playState)
    {
        this.loggedUsersKeyUserId = loggedUsersKeyUserId;
        this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
        this.server = server;
        this.playState = playState;
    }

    @Override
    public void handle(Connection connection, LogoutPacket packet)
    {
        userLoggedOut(connection);
    }

    private void userLoggedOut(Connection connection)
    {
    	User user = authenticatedClientsKeyClientId.remove(connection.getID());
    	Character userCharacter = loggedUsersKeyUserId.remove(user.getId()).userCharacter;
    	if (userCharacter != null)
    	{
    		int characterId = userCharacter.getId();
    		if (playState.has(characterId))
    		{
    			GameObject removedCharacter = playState.remove(characterId);
    			new CharacterDatabaseSaver().save((PlayerCharacter)removedCharacter);
    			server.sendToAllExceptTCP(connection.getID(), PacketsMaker.makeRemovalPacket(characterId));
    		}
    		playState.removeGameCommandsHandler(user.getId());
    	}
    }

}
