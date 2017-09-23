package pl.mmorpg.prototype.server.packetshandling;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.BuyFromShopPacket;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.GameItemsFactory;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.npcs.ShopItemWrapper;
import pl.mmorpg.prototype.server.objects.monsters.npcs.ShopNpc;
import pl.mmorpg.prototype.server.states.PlayState;

public class BuyFromShopPacketHandler extends PacketHandlerBase<BuyFromShopPacket>
{
	private PlayState playState;
	private GameDataRetriever gameData;

	public BuyFromShopPacketHandler(PlayState playState, GameDataRetriever gameData)
	{
		this.playState = playState;
		this.gameData = gameData;
	}

	@Override
	public void handle(Connection connection, BuyFromShopPacket packet)
	{
		ShopNpc shopNpc = (ShopNpc) playState.getObject(packet.getShopId());
		ShopItemWrapper itemWrapper = shopNpc.getItemWrapper(packet.getItemId());
		UserCharacter userCharacter = gameData.getUserCharacterByConnectionId(connection.getID());
		PlayerCharacter character = (PlayerCharacter) playState.getObject(userCharacter.getId());

		int itemCount = packet.getAmount();
		int singleItemPrice = itemWrapper.getPrice();
		int totalPrice = singleItemPrice * itemCount;
		int characterGold = character.getProperties().gold;
		if (totalPrice > characterGold)
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("You don't have enough gold!"));
		else
		{
			handleGoldPart(connection, totalPrice, character);
			InventoryPosition position = new InventoryPosition(packet.getDesiredInventoryPage(),
					packet.getDesiredInventoryX(), packet.getDesiredInventoryY());
			handleItemPart(connection, itemWrapper, character, itemCount, position);
		}
	}

	private void handleGoldPart(Connection connection, int totalPrice, PlayerCharacter buyer)
	{
		int characterGold = buyer.getProperties().gold;
		int goldAfterTrade = characterGold - totalPrice;
		buyer.setGold(goldAfterTrade);
		connection.sendTCP(PacketsMaker.makeGoldAmountChangePacket(goldAfterTrade));
	}

	private void handleItemPart(Connection connection, ShopItemWrapper itemWrapper, PlayerCharacter character,
			int itemCount, InventoryPosition itemInventoryPosition)
	{
		Item item = itemWrapper.getItem();
		Item newItem = GameItemsFactory.produce(item.getIdentifier(), itemCount, IdSupplier.getId(),
				itemInventoryPosition);
		character.addItem(newItem);
		connection.sendTCP(PacketsMaker.makeItemPacket(newItem));
	}
}
