package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import java.util.Collection;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import pl.mmorpg.prototype.client.userinterface.dialogs.DialogUtils;

public class MouseHoverVisibleDescriptionDialog extends MouseHoverVisibleDialog
{
	
	public MouseHoverVisibleDescriptionDialog(Actor sourceActor, String name, String description)
	{
		super(sourceActor, name);
		Collection<Label> descriptionLabels = DialogUtils.divideIntoPieces(description, 10);
		descriptionLabels.forEach(label -> 
		{
			add(label);
			row();
		});
		this.pack();
	}
	
}
