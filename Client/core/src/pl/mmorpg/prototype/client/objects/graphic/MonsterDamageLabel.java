package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.Color;

import pl.mmorpg.prototype.client.objects.GameObject;

public class MonsterDamageLabel extends MovingUpLabel
{
	public MonsterDamageLabel(int damage, GameObject source)
	{
		super(String.valueOf(damage), source, Color.RED);
		x = source.getX() + 12;
		y = source.getY() + 12;
	}

}
