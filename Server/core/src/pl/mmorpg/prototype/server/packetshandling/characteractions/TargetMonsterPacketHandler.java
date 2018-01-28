package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TargetMonsterPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.exceptions.CannotTargetItselfException;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class TargetMonsterPacketHandler extends PacketHandlerBase<TargetMonsterPacket>
{
	private PlayState playState;
	private GameDataRetriever gameDataRetriever;

	public TargetMonsterPacketHandler(PlayState playState, GameDataRetriever gameDataRetriever)
	{
		this.playState = playState;
		this.gameDataRetriever = gameDataRetriever;
	}

	@Override
	public void handle(Connection connection, TargetMonsterPacket packet)
	{
		int characterId = gameDataRetriever.getCharacterIdByConnectionId(connection.getID());
		PlayerCharacter source = (PlayerCharacter) playState.getObject(characterId);
		Monster target = (Monster) playState.getObject(packet.getMonsterId());
		tryToTargetMonster(connection, source, target);
	}

	private void tryToTargetMonster(Connection connection, PlayerCharacter source, Monster target)
	{
		try
		{
			playState.objectTargeted(source, target);
			connection.sendTCP(PacketsMaker.makeTargetingReplyPacket(target));
		} catch (CannotTargetItselfException e)
		{
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("Cannot target itself"));
		}
	}
}
