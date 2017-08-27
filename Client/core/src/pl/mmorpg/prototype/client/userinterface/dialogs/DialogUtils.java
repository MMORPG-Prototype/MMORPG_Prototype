package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class DialogUtils
{
	public static void centerPosition(Table dialog)
	{
		dialog.setPosition(Math.round((Gdx.graphics.getWidth() - dialog.getWidth()) / 2),
					Math.round((Gdx.graphics.getHeight() - dialog.getHeight()) / 2));	
	}
	
	public static Collection<Label> divideIntoPieces(String message, int maxLength)
	{
		return Arrays.stream(message.split("x(?<=\\G.{" + maxLength + "})"))
				.map(piece -> new Label(piece, Settings.DEFAULT_SKIN))
				.collect(Collectors.toList());
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
