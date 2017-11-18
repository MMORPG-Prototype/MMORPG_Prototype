package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;

import pl.mmorpg.prototype.client.objects.icons.Icon;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.ButtonField;

public abstract class QuickAccessDialog<T extends Icon> extends Dialog
{
	protected Map<Integer, ButtonField<T>> quickAccessButtons = new TreeMap<>();

	public QuickAccessDialog(String title)
	{
		super(title, Settings.DEFAULT_SKIN);

		HorizontalGroup buttons = new HorizontalGroup().padBottom(8).space(4).padTop(0).fill();
		for (int i = 0; i < 12; i++)
		{
			ButtonField<T> field = createField(i);
			quickAccessButtons.put(i, field); 
			buttons.addActor(field);
		}
		add(buttons);
		row();
		pack();
		this.setHeight(80);
		this.setMovable(false);
	}
	
	protected abstract ButtonField<T> createField(int cellPosition);

	public boolean isFieldTaken(int cellPosition)
	{
		return quickAccessButtons.get(cellPosition).hasContent();
	}
}
