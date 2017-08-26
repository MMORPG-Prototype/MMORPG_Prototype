package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.ShopItem;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ButtonCreator;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.IntegerField;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.StringValueLabel;

public class ShopBuyingDialog extends AutoCleanupOnCloseButtonDialog
{
	private IntegerField numberOfItemsField = new IntegerField(1, Settings.DEFAULT_SKIN);
	private StringValueLabel<Integer> totalPrice;
	private ShopItem item;

	public ShopBuyingDialog(ShopItem item, UserInterface linkedInterface, PacketsSender packetsSender, long shopId)
	{
		super(humanReadableFromItemIdentifier(item.getItem().getIdentifier()), linkedInterface.getDialogs(),
				item.getItem().getId());
		this.item = item;
		totalPrice = new StringValueLabel<>("Total: ", getSkin(), item.getPrice());
		Texture texture = item.getItem().getTexture();
		Image image = new Image(texture);
		Table upperContainer = new Table();
		upperContainer.add(image).width(32).height(32).padRight(43);
		upperContainer.add(numberOfItemsField).width(40).align(Align.right);
		this.getContentTable().add(upperContainer).align(Align.left);
		this.getContentTable().row();
		this.getContentTable().add(totalPrice);
		this.getContentTable().row();
		Button buyButton = ButtonCreator.createTextButton("Buy", () -> tryToBuyAction(packetsSender, shopId, linkedInterface));
		this.getContentTable().add(buyButton);
		pack();
		DialogUtils.centerPosition(this);
	}

	private static String humanReadableFromItemIdentifier(String identifier)
	{
		String noUnderScores = identifier.replaceAll("_", " ");
		String[] words = noUnderScores.split(" ");
		String result = "";
		for(String word : words)
		{
			word = word.toLowerCase();
			StringBuilder firstLetterUpper = new StringBuilder(word);
			firstLetterUpper.setCharAt(0, Character.toUpperCase(word.charAt(0)));
			result += firstLetterUpper.toString() + " ";
		}
		return result;
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);
		Integer currentTotalPrice = totalPrice.getValue();
		Integer validTotalPrice = numberOfItemsField.getValue() * item.getPrice();
		if (!currentTotalPrice.equals(validTotalPrice))
		{
			totalPrice.setValue(validTotalPrice);
			totalPrice.update();
		}
	}

	private void tryToBuyAction(PacketsSender packetsSender, long shopId, UserInterface linkedInterface)
	{
		int wantedAmount = numberOfItemsField.getValue();
		ItemInventoryPosition suitePosition = linkedInterface.getSuitePositionInInventoryFor(item);
		packetsSender.send(PacketsMaker.makeBuyFromShopPacket(shopId, item.getItem().getId(), wantedAmount, suitePosition));
	}

}
