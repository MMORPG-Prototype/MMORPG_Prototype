package pl.mmorpg.prototype.server.packetshandling;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.SpringApplicationContext;
import pl.mmorpg.prototype.clientservercommon.packets.GetUserCharactersPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.repositories.UserCharacterRepository;

public class GetUserCharacterPacketHandler extends PacketHandlerBase<GetUserCharactersPacket>
{
	private Server server;

	public GetUserCharacterPacketHandler(Server server)
	{
		this.server = server;
	}

	@Override
	public void handle(Connection connection, GetUserCharactersPacket packet)
	{
		UserCharacterRepository characterRepo = (UserCharacterRepository) SpringApplicationContext
				.getBean("userCharacterRepository");
		List<UserCharacter> userCharacters = characterRepo.findByUser_Username(packet.username);
		UserCharacterDataPacket[] charactersPackets = new UserCharacterDataPacket[userCharacters.size()];
		int i = 0;
		for (UserCharacter character : userCharacters)
		{
			charactersPackets[i] = PacketsMaker.makeCharacterPacket(character);
			i++;
		}
		server.sendToTCP(connection.getID(), charactersPackets);
	}

}
