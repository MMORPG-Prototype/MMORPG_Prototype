package pl.mmorpg.prototype.client.objects.graphic.helpers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.graphic.GraphicGameObject;

public abstract class ObjectGraphicEffectPlacer
{
	private Map<Long, ObjectGraphicEffectInfo> affectedObjects = new HashMap<>();
	private BlockingQueue<GraphicGameObject> clientGraphics;
	
	protected ObjectGraphicEffectPlacer(BlockingQueue<GraphicGameObject> clientGraphics)
	{
		this.clientGraphics = clientGraphics;
	}
	
	public void putEffect(GameObject gameObject, Supplier<Boolean> removalCondition)
	{
		if (!affectedObjects.containsKey(gameObject.getId()))
		{
			GraphicGameObject highlightGraphic = createEffect(gameObject, removalCondition);
			ObjectGraphicEffectInfo objectHighlightInfo = new ObjectGraphicEffectInfo(gameObject, highlightGraphic);
			affectedObjects.put(gameObject.getId(), objectHighlightInfo);
			clientGraphics.offer(highlightGraphic);
		}
	}
	
	protected abstract GraphicGameObject createEffect(GameObject gameObject, Supplier<Boolean> removalCondition);
	
	public void update()
	{
		Iterator<ObjectGraphicEffectInfo> iterator = affectedObjects.values().iterator();
		while(iterator.hasNext())
		{
			ObjectGraphicEffectInfo effectInfo = iterator.next();
			if(effectInfo.getGraphicEffect().shouldDelete())
			{
				effectInfo.getGraphicEffect().dispose();
				iterator.remove();
			}
		}
	}
}
