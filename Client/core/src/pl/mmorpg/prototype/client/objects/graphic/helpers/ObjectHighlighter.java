package pl.mmorpg.prototype.client.objects.graphic.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.graphic.GameObjectHighlightGraphic;
import pl.mmorpg.prototype.client.objects.graphic.GraphicGameObject;

public class ObjectHighlighter
{
	private Map<Long, ObjectHighlightInfo> highlightedObjects = new HashMap<>();
	private BlockingQueue<GraphicGameObject> clientGraphics;
	
	public ObjectHighlighter(BlockingQueue<GraphicGameObject> clientGraphics)
	{
		this.clientGraphics = clientGraphics;
	}
	
	public void highlight(GameObject gameObject, Supplier<Boolean> removalCondition)
	{
		if (!highlightedObjects.containsKey(gameObject.getId()))
		{
			GameObjectHighlightGraphic highlightGraphic = new GameObjectHighlightGraphic(gameObject, removalCondition);
			ObjectHighlightInfo objectHighlightInfo = new ObjectHighlightInfo(gameObject, highlightGraphic);
			highlightedObjects.put(gameObject.getId(), objectHighlightInfo);
			clientGraphics.offer(highlightGraphic);
		}
	}
	
	public void update()
	{
		highlightedObjects.values().removeIf(info -> info.getHighlightGraphic().shouldDelete());
	}
}
