package pl.mmorpg.prototype.server.packetshandling;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.BuyFromShopPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.GameItemsFactory;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.npcs.ShopItemWrapper;
import pl.mmorpg.prototype.server.objects.monsters.npcs.ShopNpc;
import pl.mmorpg.prototype.server.states.PlayState;

public class BuyFromShopPacketHandler extends PacketHandlerBase<BuyFromShopPacket>
{
	private PlayState playState;
	private Map<Integer, UserInfo> loggedUsersKeyUserId;
	private Map<Integer, User> authenticatedClientsKeyClientId;

	public BuyFromShopPacketHandler(PlayState playState, Map<Integer, UserInfo> loggedUsersKeyUserId,
			Map<Integer, User> authenticatedClientsKeyClientId)
	{
		this.playState = playState;
		this.loggedUsersKeyUserId = loggedUsersKeyUserId;
		this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
	}

	@Override
	public void handle(Connection connection, BuyFromShopPacket packet)
	{
		ShopNpc shopNpc = (ShopNpc) playState.getObject(packet.getShopId());
		ShopItemWrapper itemWrapper = shopNpc.getItemWrapper(packet.getItemId());
		UserCharacter userCharacter = PacketHandlingHelper.getUserCharacterByConnectionId(connection.getID(),
				loggedUsersKeyUserId, authenticatedClientsKeyClientId);
		PlayerCharacter character = (PlayerCharacter) playState.getObject(userCharacter.getId());

		int itemCount = packet.getAmount();
		int singleItemPrice = itemWrapper.getPrice();
		int totalPrice = singleItemPrice * itemCount;
		int characterGold = character.getProperties().gold;
		if(totalPrice > characterGold)
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("You don't have enough gold!"));
		else
		{
			handleGoldPart(connection, totalPrice, character);
			handleItemPart(connection, itemWrapper, character, itemCount);
		}
		
	}
	
	private void handleGoldPart(Connection connection, int totalPrice, PlayerCharacter buyer)
	{
		int characterGold = buyer.getProperties().gold;
		int goldAfterTrade = characterGold - totalPrice;
		buyer.setGold(goldAfterTrade);
		connection.sendTCP(PacketsMaker.makeGoldAmountChangePacket(goldAfterTrade));
	}
	
	private void handleItemPart(Connection connection, ShopItemWrapper itemWrapper, PlayerCharacter character, int itemCount)
	{
		Item item = itemWrapper.getItem();
		Item newItem = GameItemsFactory.produce(item.getIdentifier(), itemCount, IdSupplier.getId());
		character.addItem(newItem);
		connection.sendTCP(PacketsMaker.makeItemPacket(newItem));
	}
}
