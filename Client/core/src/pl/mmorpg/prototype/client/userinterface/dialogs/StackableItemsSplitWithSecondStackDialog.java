package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.StackableItem;

public class StackableItemsSplitWithSecondStackDialog extends StackableItemsSplitDialog
{

	public StackableItemsSplitWithSecondStackDialog(StackableItem sourceItem, StackableItem destinationItem,
			ItemInventoryPosition destinationPosition, ActorManipulator linkedManipulator, PacketsSender packetsSender,
			long id)
	{
		super(sourceItem, destinationItem, destinationPosition, linkedManipulator, packetsSender, id);
	}

	@Override
	protected Slider createSlider(StackableItem sourceItem, StackableItem destinationItem)
	{
		int allItemsCount = sourceItem.getItemCount() + destinationItem.getItemCount();
		Slider slider = new Slider(1, allItemsCount - 1, 1, false, getSkin());
		slider.setValue(destinationItem.getItemCount());
		slider.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				int currentValue = (int) slider.getValue();
				startingFieldItemNumber.setText(allItemsCount - currentValue);
				destinationFieldItemNumber.setText(currentValue);
			}
		});
		return slider;
	}

	@Override
	protected int getStartingFieldItemNumber(StackableItem sourceItem)
	{
		return sourceItem.getItemCount();
	}

	@Override
	protected int getDestinationFieldItemNumber(StackableItem destinationItem)
	{
		return destinationItem.getItemCount();
	}

	@Override
	protected void onOkButton()
	{
		packetsSender.send(PacketsMaker.makeSplitItemsPacket(sourceItem,
				sourceItem.getItemCount() - startingFieldItemNumber.getIntValue(), destinationPosition));
	}

}
