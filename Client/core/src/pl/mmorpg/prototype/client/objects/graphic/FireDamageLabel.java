package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.Color;

import pl.mmorpg.prototype.client.objects.GameObject;

public class FireDamageLabel extends MovingUpLabel
{
    public FireDamageLabel(int damage, GameObject source)
    {
        super(String.valueOf(damage), source, Color.ORANGE);
    }
}
