package pl.mmorpg.prototype.server.packetshandling;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterChangePacket;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.DisconnectPacket;
import pl.mmorpg.prototype.clientservercommon.packets.GetUserCharactersPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.LogoutPacket;
import pl.mmorpg.prototype.clientservercommon.packets.NpcConversationAnwserChoosenPacket;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptCodePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveDownPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveLeftPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveRightPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveUpPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.AcceptQuestPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.BuyFromShopPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.GetQuestBoardInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.InventoryItemRepositionRequestPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemPutInQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemRemovedFromQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.NpcDialogStartPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.OpenContainterPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.OpenShopPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.RetrieveGoldRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.RetrieveItemRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SpellPutInQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SpellRemovedFromQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SplitItemsPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakeItemFromContainerPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakingGoldFromContainerPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TargetMonsterPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.spells.FireballSpellUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.spells.HealSpellUsagePacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.exceptions.UnknownPacketTypeException;
import pl.mmorpg.prototype.server.packetshandling.characteractions.AcceptQuestPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.GetQuestBoardInfoPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.InventoryItemRepositionRequestPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.ItemPutInQuickAccessBarPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.ItemRemovedFromQuickAccessBarPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.ItemUsagePacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.MoveDownPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.MoveLeftPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.MoveRightPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.MoveUpPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.NpcConversationAnwserChoosenPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.NpcDialogStartPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.OpenContainerPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.OpenShopPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.RetrieveGoldRewardPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.RetrieveItemRewardPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.SpellPutInQuickAccessBarPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.SpellRemovedFromQuickAccessBarPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.SplitItemsPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.TakeItemFromContainerPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.TakingGoldFromContainerPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.TargetMonsterPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.spell.usage.FireballSpellUsagePacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.spell.usage.HealSpellUsagePacketHandler;
import pl.mmorpg.prototype.server.states.PlayState;

public class PacketHandlerFactory
{
	private Map<Class<?>, PacketHandler> packetHandlers = new HashMap<>();

	public PacketHandlerFactory(Map<Integer, UserInfo> loggedUsersKeyUserId,
			Map<Integer, User> authenticatedClientsKeyClientId, Server server, PlayState playState)
	{
		GameDataRetriever gameDataRetriever = new GameDataRetriever(loggedUsersKeyUserId,
				authenticatedClientsKeyClientId);

		packetHandlers.put(AuthenticationPacket.class, new AuthenticationPacketHandler(loggedUsersKeyUserId,
				authenticatedClientsKeyClientId, server, playState));
		packetHandlers.put(CharacterCreationPacket.class,
				new CharacterCreationPacketHandler(authenticatedClientsKeyClientId, server));
		packetHandlers.put(CharacterChangePacket.class, new CharacterChangePacketHandler(loggedUsersKeyUserId,
				authenticatedClientsKeyClientId, playState, server));
		packetHandlers.put(DisconnectPacket.class, new DisconnectPacketHandler());
		packetHandlers.put(GetUserCharactersPacket.class, new GetUserCharacterPacketHandler(server));
		packetHandlers.put(LogoutPacket.class,
				new LogoutPacketHandler(loggedUsersKeyUserId, authenticatedClientsKeyClientId, server, playState));
		packetHandlers.put(RegisterationPacket.class, new RegisterationPacketHandler(server));
		packetHandlers.put(UserCharacterDataPacket.class,
				new UserCharacterDataPacketHandler(loggedUsersKeyUserId, server, playState));
		packetHandlers.put(MoveLeftPacket.class, new MoveLeftPacketHandler(playState));
		packetHandlers.put(MoveRightPacket.class, new MoveRightPacketHandler(playState));
		packetHandlers.put(MoveUpPacket.class, new MoveUpPacketHandler(playState));
		packetHandlers.put(MoveDownPacket.class, new MoveDownPacketHandler(playState));
		packetHandlers.put(ChatMessagePacket.class, new ChatMessagePacketHandler(server, gameDataRetriever));
		packetHandlers.put(ItemUsagePacket.class, new ItemUsagePacketHandler(gameDataRetriever, playState, server));
		packetHandlers.put(FireballSpellUsagePacket.class,
				new FireballSpellUsagePacketHandler(gameDataRetriever, playState));
		packetHandlers.put(HealSpellUsagePacket.class, new HealSpellUsagePacketHandler(gameDataRetriever, playState));
		packetHandlers.put(OpenContainterPacket.class, new OpenContainerPacketHandler(server, playState));
		packetHandlers.put(TakeItemFromContainerPacket.class,
				new TakeItemFromContainerPacketHandler(gameDataRetriever, server, playState));
		packetHandlers.put(TakingGoldFromContainerPacket.class,
				new TakingGoldFromContainerPacketHandler(gameDataRetriever, server, playState));
		packetHandlers.put(BuyFromShopPacket.class, new BuyFromShopPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(ScriptCodePacket.class,
				new ScriptCodePacketHandler(playState, authenticatedClientsKeyClientId));
		packetHandlers.put(InventoryItemRepositionRequestPacket.class,
				new InventoryItemRepositionRequestPacketHandler(gameDataRetriever, playState));
		packetHandlers.put(ItemPutInQuickAccessBarPacket.class,
				new ItemPutInQuickAccessBarPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(ItemRemovedFromQuickAccessBarPacket.class,
				new ItemRemovedFromQuickAccessBarPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(SpellPutInQuickAccessBarPacket.class,
				new SpellPutInQuickAccessBarPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(SpellRemovedFromQuickAccessBarPacket.class,
				new SpellRemovedFromQuickAccessBarPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(SplitItemsPacket.class, new SplitItemsPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(RetrieveItemRewardPacket.class,
				new RetrieveItemRewardPacketHandler(gameDataRetriever, playState));
		packetHandlers.put(RetrieveGoldRewardPacket.class,
				new RetrieveGoldRewardPacketHandler(gameDataRetriever, playState));
		packetHandlers.put(AcceptQuestPacket.class, new AcceptQuestPacketHandler(gameDataRetriever));
		packetHandlers.put(NpcConversationAnwserChoosenPacket.class,
				new NpcConversationAnwserChoosenPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(OpenShopPacket.class, new OpenShopPacketHandler(playState));
		packetHandlers.put(TargetMonsterPacket.class, new TargetMonsterPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(GetQuestBoardInfoPacket.class,
				new GetQuestBoardInfoPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(NpcDialogStartPacket.class, new NpcDialogStartPacketHandler(playState, gameDataRetriever));

		// Ignore framework packets
		packetHandlers.put(FrameworkMessage.KeepAlive.class, new NullPacketHandler());
	}

	public PacketHandler produce(Object object)
	{
		PacketHandler packetHandler = packetHandlers.get(object.getClass());
		if (packetHandler == null)
			throw new UnknownPacketTypeException(object);
		return packetHandler;
	}
}
