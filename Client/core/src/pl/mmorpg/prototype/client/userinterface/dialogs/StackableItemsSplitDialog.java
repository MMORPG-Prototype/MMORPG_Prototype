package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.ItemIdentifier;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.StackableItem;
import pl.mmorpg.prototype.client.objects.icons.Icon;
import pl.mmorpg.prototype.client.objects.icons.items.Item;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.IntegerLabel;

public abstract class StackableItemsSplitDialog extends AutoCleanupOnCloseButtonDialog
{
	protected final IntegerLabel startingFieldItemNumber;
	protected final IntegerLabel destinationFieldItemNumber;
	protected final PacketsSender packetsSender;
	protected final StackableItem sourceItem;
	protected final ItemInventoryPosition destinationPosition;

	protected StackableItemsSplitDialog(StackableItem sourceItem, StackableItem destinationItem,
			ItemInventoryPosition destinationPosition, ActorManipulator linkedManipulator, PacketsSender packetsSender,
			long id)
	{
		super(getDialogName(sourceItem), linkedManipulator, id);
		this.sourceItem = sourceItem;
		this.destinationPosition = destinationPosition;
		this.packetsSender = packetsSender;

		startingFieldItemNumber = new IntegerLabel(getStartingFieldItemNumber(sourceItem), getSkin());
		destinationFieldItemNumber = new IntegerLabel(getDestinationFieldItemNumber(destinationItem), getSkin());

		addUpperSection(sourceItem);
		addSlider(sourceItem, destinationItem);
		addOkButton();
		pack();
	}

	protected abstract Slider createSlider(StackableItem sourceItem, StackableItem destinationItem);

	protected abstract int getStartingFieldItemNumber(StackableItem sourceItem);
	
	protected abstract int getDestinationFieldItemNumber(StackableItem destinationItem);

	protected void addUpperSection(StackableItem item)
	{
		Table upperGroup = new Table();
		upperGroup.add(createIconLinkedWithLabel(item.getTexture(), startingFieldItemNumber).padLeft(10));
		upperGroup.add(new Icon(Assets.get("right-arrow.png"), 48, 24)).padLeft(25).padRight(25);
		upperGroup.add(createIconLinkedWithLabel(item.getTexture(), destinationFieldItemNumber).padRight(10));
		getContentTable().add(upperGroup);
		getContentTable().row();
	}
	
	private VerticalGroup createIconLinkedWithLabel(Texture texture, Label label)
	{
		VerticalGroup startingFieldItemGroup = new VerticalGroup();
		startingFieldItemGroup.addActor(new Icon(texture));
		startingFieldItemGroup.addActor(label);
		return startingFieldItemGroup;
	}
	
	private void addSlider(StackableItem sourceItem, StackableItem destinationItem)
	{
		getContentTable().add(createSlider(sourceItem, destinationItem)).center();
	}

	protected void addOkButton()
	{
		TextButton button = new TextButton("Ok", getSkin());
		button.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				onOkButton();
				cleanUpItself();
			}
		});
		button(button);
	}
	
	protected abstract void onOkButton();

	protected static String getDialogName(Item item)
	{
		String identifier = ItemIdentifier.getIdentifier(item.getClass());
		String itemName = DialogUtils.humanReadableFromItemIdentifier(identifier);
		return "Split " + itemName;
	}

}
