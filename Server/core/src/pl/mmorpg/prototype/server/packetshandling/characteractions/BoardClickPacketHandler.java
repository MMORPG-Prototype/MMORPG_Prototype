package pl.mmorpg.prototype.server.packetshandling.characteractions;

import org.apache.commons.lang3.NotImplementedException;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.BoardClickPacket;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.MapCollisionUnknownObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.npcs.QuestDialogNpc;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.quests.events.NpcDialogStartEvent;
import pl.mmorpg.prototype.server.states.PlayState;

public class BoardClickPacketHandler extends PacketHandlerBase<BoardClickPacket>
{
	private PlayState playState;
	private GameDataRetriever gameData;

	public BoardClickPacketHandler(PlayState playState, GameDataRetriever gameData)
	{
		this.playState = playState;
		this.gameData = gameData;
	}

	@Override
	public void handle(Connection connection, BoardClickPacket packet)
	{
		GameObject target = playState.getCollisionMap().getTopObject(packet.gameX, packet.gameY);
		if (target != null && !(target instanceof MapCollisionUnknownObject))
		{
			Character userCharacter = gameData.getUserCharacterByConnectionId(connection.getID());
			PlayerCharacter source = (PlayerCharacter) playState.getObject(userCharacter.getId());

			clientBoardClickProperHandle(connection, target, source);
		}
	}

	private void clientBoardClickProperHandle(Connection connection, GameObject target, PlayerCharacter source)
	{
		// TODO: refactor, implement collision detection on client side, so he 
		// can tell what he wants to do directly
		if (target instanceof QuestDialogNpc)
			propagateQuestDialogEvent((QuestDialogNpc) target, source);
		else if (target.getClass().getSimpleName().contains("NullGameObject"))
			;//ignore
		else
			throw new NotImplementedException("Not implemented");

	}
	
	private void propagateQuestDialogEvent(QuestDialogNpc npc, PlayerCharacter player)
	{
		Event talkWithNpcEvent = new NpcDialogStartEvent(npc, (PacketsSender)playState);
		talkWithNpcEvent.addReceiver(player);
		playState.enqueueEvent(talkWithNpcEvent);
	}

}
