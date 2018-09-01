package pl.mmorpg.prototype.server.packetshandling;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.packets.GetUserCharactersPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.repositories.CharacterRepository;

public class GetUserCharacterPacketHandler extends PacketHandlerBase<GetUserCharactersPacket>
{
	private final GameDataRetriever gameDataRetriever;

	public GetUserCharacterPacketHandler(GameDataRetriever gameDataRetriever)
	{
		this.gameDataRetriever = gameDataRetriever;
	}

	@Override
	public void handle(Connection connection, GetUserCharactersPacket packet)
	{
		User user = gameDataRetriever.getUserByConnectionId(connection.getID());
		CharacterRepository characterRepo = SpringContext.getBean(CharacterRepository.class);
		List<Character> userCharacters = characterRepo.findByUser_Username(user.getUsername());
		UserCharacterDataPacket[] charactersPackets = new UserCharacterDataPacket[userCharacters.size()];
		int i = 0;
		for (Character character : userCharacters)
		{
			charactersPackets[i] = PacketsMaker.makeCharacterPacket(character);
			i++;
		}
		connection.sendTCP(charactersPackets);
	}

}
