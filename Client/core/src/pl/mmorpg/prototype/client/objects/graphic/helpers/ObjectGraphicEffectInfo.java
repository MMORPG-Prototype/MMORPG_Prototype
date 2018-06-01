package pl.mmorpg.prototype.client.objects.graphic.helpers;

import lombok.Data;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.graphic.GraphicGameObject;

@Data
public class ObjectGraphicEffectInfo<T extends GraphicGameObject>
{
	private final GameObject gameObject;
	private final T graphicEffect;
}
