package pl.mmorpg.prototype.client.objects.graphic.helpers;

import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.graphic.GameObjectHighlightGraphic;
import pl.mmorpg.prototype.client.objects.graphic.GraphicGameObject;

public class ObjectHighlighter extends ObjectGraphicEffectPlacer
{
	public ObjectHighlighter(BlockingQueue<GraphicGameObject> clientGraphics)
	{
		super(clientGraphics);
	}
	
	@Override
	protected GameObjectHighlightGraphic createEffect(GameObject gameObject, Supplier<Boolean> removalCondition)
	{
		return new GameObjectHighlightGraphic(gameObject, removalCondition);
	}

}
