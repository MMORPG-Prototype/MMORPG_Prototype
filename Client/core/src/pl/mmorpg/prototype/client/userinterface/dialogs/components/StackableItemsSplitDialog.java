package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.ItemIdentifier;
import pl.mmorpg.prototype.client.items.StackableItem;
import pl.mmorpg.prototype.client.objects.icons.Icon;
import pl.mmorpg.prototype.client.objects.icons.items.Item;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.dialogs.DialogUtils;

public class StackableItemsSplitDialog extends AutoCleanupOnCloseButtonDialog
{
	private final IntegerLabel startingFieldItemNumber;
	private final IntegerLabel destinationFieldItemNumber;

	public StackableItemsSplitDialog(StackableItem item, ActorManipulator linkedManipulator, long id)
	{
		super(getDialogName(item), linkedManipulator, id);
		startingFieldItemNumber = new IntegerLabel(item.getItemCount() - 1, getSkin());
		destinationFieldItemNumber = new IntegerLabel(1, getSkin());

		addUpperSection(item);
		addSlider(item);
		addOkButton();
		pack();
	}

	private void addUpperSection(StackableItem item)
	{
		Table upperGroup = new Table();
		upperGroup.add(createIconLinkedWithLabel(item.getTexture(), startingFieldItemNumber).padLeft(10));
		upperGroup.add(new Icon(Assets.get("right-arrow.png"), 48, 24)).padLeft(25).padRight(25);
		upperGroup.add(createIconLinkedWithLabel(item.getTexture(), destinationFieldItemNumber).padRight(10));
		getContentTable().add(upperGroup);
		getContentTable().row();
	}

	private void addSlider(StackableItem item)
	{
		Slider slider = new Slider(1, item.getItemCount() - 1, 1, false, getSkin());
		slider.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				int destinationSplitNumber = (int) slider.getValue();
				startingFieldItemNumber.setText(item.getItemCount() - destinationSplitNumber);
				destinationFieldItemNumber.setText(destinationSplitNumber);
			}
		});
		getContentTable().add(slider).center();
	}

	private void addOkButton()
	{
		TextButton button = new TextButton("Ok", getSkin());
		button.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				cleanUpItself();
			}
		});
		button(button);
	}
	
	private VerticalGroup createIconLinkedWithLabel(Texture texture, Label label)
	{
		VerticalGroup startingFieldItemGroup = new VerticalGroup();
		startingFieldItemGroup.addActor(new Icon(texture));
		startingFieldItemGroup.addActor(label);
		return startingFieldItemGroup;
	}

	private static String getDialogName(Item item)
	{
		String identifier = ItemIdentifier.getObjectIdentifier(item.getClass());
		String itemName = DialogUtils.humanReadableFromItemIdentifier(identifier);
		return "Split " + itemName;
	}

}
