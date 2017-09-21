package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import org.apache.commons.text.WordUtils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MouseHoverVisibleDescriptionDialog extends MouseHoverVisibleDialog
{

    public MouseHoverVisibleDescriptionDialog(Actor sourceActor, String name, String description)
    {
        super(sourceActor, name);
        String[] descriptionLines = WordUtils.wrap(description, 20).split(System.lineSeparator());
        for(String descriptionLine : descriptionLines)
        {
            Label descriptionLabel = new Label(descriptionLine, getSkin());
            getContentTable().add(descriptionLabel);
            getContentTable().row();   
        }
        this.pack();
    }

}
