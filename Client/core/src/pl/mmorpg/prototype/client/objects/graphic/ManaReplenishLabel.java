package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.Color;

import pl.mmorpg.prototype.client.objects.GameObject;

public class ManaReplenishLabel extends MovingUpLabel
{
	public ManaReplenishLabel(String message, GameObject source)
	{
		super(message, source, Color.BLUE);
	}

}
