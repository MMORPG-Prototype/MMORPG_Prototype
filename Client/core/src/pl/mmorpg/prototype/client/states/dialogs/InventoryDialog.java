package pl.mmorpg.prototype.client.states.dialogs;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.PlayState;

public class InventoryDialog extends Window
{
	private PlayState linkedState;
	private ButtonGroup<Button> inventoryCellsGroup = new ButtonGroup<>();

	private Map<Point, Button> inventoryCells = new HashMap<>();

	public InventoryDialog(PlayState linkedState)
	{
		super("Inventory", Settings.DEFAULT_SKIN);
		this.linkedState = linkedState;

		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				Point cellPosition = new Point(i, j);
				Button button = new ImageButton(Settings.DEFAULT_SKIN);
				button.setWidth(20);
				button.setHeight(20);
				button.add(new Image((Texture) Assets.get("MainChar.png")));
				inventoryCells.put(cellPosition, button);
				inventoryCellsGroup.add(button);
				add(button);
			}
			row();
		}
	}

}
