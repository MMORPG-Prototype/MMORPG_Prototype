package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.StackableItem;

public class StackableItemsSplitSingleStackDialog extends StackableItemsSplitDialog
{
	public StackableItemsSplitSingleStackDialog(StackableItem sourceItem, ItemInventoryPosition destinationPosition,
			ActorManipulator linkedManipulator, PacketsSender packetsSender, long id)
	{
		super(sourceItem, null, destinationPosition, linkedManipulator, packetsSender, id);
	}

	@Override
	protected Slider createSlider(StackableItem sourceItem, StackableItem destinationItem)
	{
		Slider slider = new Slider(1, sourceItem.getItemCount() - 1, 1, false, getSkin());
		slider.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				int destinationSplitNumber = (int) slider.getValue();
				startingFieldItemNumber.setText(sourceItem.getItemCount() - destinationSplitNumber);
				destinationFieldItemNumber.setText(destinationSplitNumber);
			}
		});
		return slider;
	}

	@Override
	protected int getStartingFieldItemNumber(StackableItem sourceItem)
	{
		return sourceItem.getItemCount() - 1;
	}

	@Override
	protected int getDestinationFieldItemNumber(StackableItem destinationItem)
	{
		return 1;
	}

	@Override
	protected void onOkButton()
	{
		packetsSender.send(
				PacketsMaker.makeSplitItemsPacket(sourceItem, destinationFieldItemNumber.getIntValue(), destinationPosition));
	}
}
