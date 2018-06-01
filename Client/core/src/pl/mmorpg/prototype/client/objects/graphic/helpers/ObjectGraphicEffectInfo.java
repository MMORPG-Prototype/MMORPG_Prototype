package pl.mmorpg.prototype.client.objects.graphic.helpers;

import lombok.Data;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.graphic.GraphicGameObject;

@Data
public class ObjectGraphicEffectInfo
{
	private final GameObject gameObject;
	private final GraphicGameObject graphicEffect;
}
