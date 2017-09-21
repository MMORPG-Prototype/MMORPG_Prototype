package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DialogUtils
{
	public static void centerPosition(Table dialog)
	{
		dialog.setPosition(Math.round((Gdx.graphics.getWidth() - dialog.getWidth()) / 2),
					Math.round((Gdx.graphics.getHeight() - dialog.getHeight()) / 2));	
	}
	
	public static Actor getTopActorOf(Actor actor)
	{
		Actor topDialogActor = actor;
		while(true)
		{
			Actor candidate = topDialogActor.getParent();
			if(!candidate.hasParent())
				break;
			topDialogActor = candidate;
		}
		return topDialogActor;
	}
}
