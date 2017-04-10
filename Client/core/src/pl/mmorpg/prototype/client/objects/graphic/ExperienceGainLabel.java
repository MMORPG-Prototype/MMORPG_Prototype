package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.Color;

import pl.mmorpg.prototype.client.objects.GameObject;

public class ExperienceGainLabel extends MovingUpLabel
{
	
	public ExperienceGainLabel(String message, GameObject source)
	{
		super(message, source, Color.WHITE);
		x = source.getX() + 12;
		y = source.getY() + 12;
	}
	
}
