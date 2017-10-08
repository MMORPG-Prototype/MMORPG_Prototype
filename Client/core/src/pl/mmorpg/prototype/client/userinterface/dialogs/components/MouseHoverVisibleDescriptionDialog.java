package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class MouseHoverVisibleDescriptionDialog extends MouseHoverVisibleDialog
{

    public MouseHoverVisibleDescriptionDialog(Actor sourceActor, String name, String description)
    {
        super(sourceActor, name);
        LineBreaker lineBreaker = new LineBreaker(description, 20);
        getContentTable().add(lineBreaker);
        this.pack();
    }

}
