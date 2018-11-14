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
import pl.mmorpg.prototype.clientservercommon.packets.NpcConversationAnswerChosenPacket;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptCodePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.levelup.UseLevelUpPointOnDexterityPacket;
import pl.mmorpg.prototype.clientservercommon.packets.levelup.UseLevelUpPointOnIntelligencePacket;
import pl.mmorpg.prototype.clientservercommon.packets.levelup.UseLevelUpPointOnStrengthPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveDownPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveLeftPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveRightPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveUpPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.*;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.spells.FireballSpellUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.spells.HealSpellUsagePacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.exceptions.UnknownPacketTypeException;
import pl.mmorpg.prototype.server.packetshandling.characteractions.*;
import pl.mmorpg.prototype.server.packetshandling.characteractions.spell.usage.FireballSpellUsagePacketHandler;
import pl.mmorpg.prototype.server.packetshandling.characteractions.spell.usage.HealSpellUsagePacketHandler;
import pl.mmorpg.prototype.server.packetshandling.levelup.UseLevelUpPointOnDexterityPacketHandler;
import pl.mmorpg.prototype.server.packetshandling.levelup.UseLevelUpPointOnIntelligencePacketHandler;
import pl.mmorpg.prototype.server.packetshandling.levelup.UseLevelUpPointOnStrengthPacketHandler;
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
		packetHandlers.put(GetUserCharactersPacket.class, new GetUserCharacterPacketHandler(gameDataRetriever));
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
		packetHandlers.put(NpcConversationAnswerChosenPacket.class,
				new NpcConversationAnswerChosenPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(OpenShopPacket.class, new OpenShopPacketHandler(playState));
		packetHandlers.put(TargetMonsterPacket.class, new TargetMonsterPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(GetQuestBoardInfoPacket.class,
				new GetQuestBoardInfoPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(
				NpcDialogStartRequestPacket.class, new NpcDialogStartPacketHandler(playState, gameDataRetriever));
		packetHandlers.put(UseLevelUpPointOnStrengthPacket.class, new UseLevelUpPointOnStrengthPacketHandler(gameDataRetriever, playState));
		packetHandlers.put(UseLevelUpPointOnIntelligencePacket.class, new UseLevelUpPointOnIntelligencePacketHandler(gameDataRetriever, playState));
		packetHandlers.put(UseLevelUpPointOnDexterityPacket.class, new UseLevelUpPointOnDexterityPacketHandler(gameDataRetriever, playState));
		packetHandlers.put(EquipItemPacket.class, new EquipItemPacketHandler(gameDataRetriever, playState));
		packetHandlers.put(TakeOffItemPacket.class, new TakeOffItemPacketHandler(gameDataRetriever, playState));

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
