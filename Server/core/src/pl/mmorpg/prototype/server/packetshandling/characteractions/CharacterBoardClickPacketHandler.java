package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.ShopItemsPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.BoardClickPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.exceptions.CannotTargetItselfException;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.MapCollisionUnknownObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.ineractivestaticobjects.QuestBoard;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.npcs.QuestDialogNpc;
import pl.mmorpg.prototype.server.objects.monsters.npcs.ShopItemWrapper;
import pl.mmorpg.prototype.server.objects.monsters.npcs.ShopNpc;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.quests.events.NpcDialogStartEvent;
import pl.mmorpg.prototype.server.states.PlayState;

public class CharacterBoardClickPacketHandler extends PacketHandlerBase<BoardClickPacket>
{
	private PlayState playState;
	private GameDataRetriever gameData;

	public CharacterBoardClickPacketHandler(PlayState playState, GameDataRetriever gameData)
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
		// TODO: refactor, implement collicion detection on clent side, so he 
		// can tell what he wants to do directly
		if (target instanceof ShopNpc)
			sendShopItemsInfo(connection, (ShopNpc) target);
		else if (target instanceof QuestDialogNpc)
			propagateQuestDialogEvent((QuestDialogNpc) target, source);
		else if (target instanceof Monster)
			tryToTargetMonster(connection, (Monster) target, source);
		else if (target instanceof QuestBoard)
			sendQuestBoardInfo(connection, (QuestBoard) target);
		else if (target.getClass().getSimpleName().contains("NullGameObject"))
			;//ignore
		else
			throw new NotImplementedException("Not implemented");

	}

	private void sendShopItemsInfo(Connection connection, ShopNpc source)
	{
		Collection<ShopItemWrapper> availableItems = source.getAvailableItems();
		ShopItemsPacket shopItemsPacket = PacketsMaker.makeShopItemsPacket(availableItems, source.getId());
		connection.sendTCP(shopItemsPacket);
	}

	private void propagateQuestDialogEvent(QuestDialogNpc npc, PlayerCharacter player)
	{
		Event talkWithNpcEvent = new NpcDialogStartEvent(npc, (PacketsSender)playState);
		talkWithNpcEvent.addReceiver(player);
		playState.enqueueEvent(talkWithNpcEvent);
	}

	private void tryToTargetMonster(Connection connection, Monster target, PlayerCharacter source)
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

	private void sendQuestBoardInfo(Connection connection, QuestBoard questBoard)
	{
		Collection<CharactersQuests> characterQuests = gameData.getCharacterQuestsByConnectionId(connection.getID());
		List<Quest> quests = characterQuests.stream().map(CharactersQuests::getQuest).collect(Collectors.toList());
		Predicate<Quest> shouldQuestBeIncluded = quest -> !quests.contains(quest);
		connection.sendTCP(PacketsMaker.makeQuestBoardInfoPacket(questBoard, shouldQuestBeIncluded));
	}

}
