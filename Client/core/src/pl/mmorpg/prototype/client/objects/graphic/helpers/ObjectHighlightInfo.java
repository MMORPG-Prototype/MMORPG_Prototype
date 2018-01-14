package pl.mmorpg.prototype.client.objects.graphic.helpers;

import lombok.Data;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.graphic.GameObjectHighlightGraphic;

@Data
public class ObjectHighlightInfo
{
	private final GameObject gameObject;
	private final GameObjectHighlightGraphic highlightGraphic;
}
