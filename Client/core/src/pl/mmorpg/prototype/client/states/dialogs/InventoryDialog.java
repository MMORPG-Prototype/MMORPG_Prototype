package pl.mmorpg.prototype.client.states.dialogs;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.dialogs.components.InventoryField;

public class InventoryDialog extends CustomDialog
{
	private PlayState linkedState;

	private Map<Point, Button> inventoryCells = new HashMap<>();

	public InventoryDialog(PlayState linkedState)
	{
		super("Inventory", Settings.DEFAULT_SKIN);
		this.linkedState = linkedState;
		VerticalGroup v = new VerticalGroup().space(0).pad(0).fill();
		for (int i = 0; i < 5; i++)
		{
			HorizontalGroup g = new HorizontalGroup().space(0).pad(0).fill();
			for (int j = 0; j < 5; j++)
			{
				Point cellPosition = new Point(i, j);
				Button button = createButton(cellPosition);
				inventoryCells.put(cellPosition, button);
				g.addActor(button);
			}
			v.addActor(g);
		}
		v.padBottom(8);
		add(v);
		row();
	}

	private Button createButton(Point cellPosition)
	{
		InventoryField button = new InventoryField();
		button.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				buttonClicked(cellPosition);
			}
		});

		return button;
	}

	@Override
	public CustomDialogs getIdentifier()
	{
		return CustomDialogs.INVENTORY;
	}

	public void buttonClicked(Point cellPosition)
	{
		System.out.println(cellPosition);
	}

}
