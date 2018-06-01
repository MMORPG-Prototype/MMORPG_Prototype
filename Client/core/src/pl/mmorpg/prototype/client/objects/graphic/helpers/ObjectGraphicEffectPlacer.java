package pl.mmorpg.prototype.client.objects.graphic.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.graphic.GraphicGameObject;

public abstract class ObjectGraphicEffectPlacer<T extends GraphicGameObject>
{
	private Map<Long, ObjectGraphicEffectInfo<T>> affectedObjects = new HashMap<>();
	private BlockingQueue<GraphicGameObject> clientGraphics;
	
	protected ObjectGraphicEffectPlacer(BlockingQueue<GraphicGameObject> clientGraphics)
	{
		this.clientGraphics = clientGraphics;
	}
	
	public void putEffect(GameObject gameObject, Supplier<Boolean> removalCondition)
	{
		if (!affectedObjects.containsKey(gameObject.getId()))
		{
			T highlightGraphic = createEffect(gameObject, removalCondition);
			ObjectGraphicEffectInfo<T> objectHighlightInfo = new ObjectGraphicEffectInfo<>(gameObject, highlightGraphic);
			affectedObjects.put(gameObject.getId(), objectHighlightInfo);
			clientGraphics.offer(highlightGraphic);
		}
	}
	
	protected abstract T createEffect(GameObject gameObject, Supplier<Boolean> removalCondition);
	
	public void update()
	{
		affectedObjects.values().removeIf(info -> info.getGraphicEffect().shouldDelete());
	}
}
