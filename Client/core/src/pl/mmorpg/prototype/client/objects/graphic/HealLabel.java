package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.Color;

import pl.mmorpg.prototype.client.objects.GameObject;

public class HealLabel extends MovingUpLabel
{
	public HealLabel(String message, GameObject source)
	{
		super(message, source, Color.GREEN);
	}

}
