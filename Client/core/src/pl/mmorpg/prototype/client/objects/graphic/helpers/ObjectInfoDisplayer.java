package pl.mmorpg.prototype.client.objects.graphic.helpers;

import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.graphic.GameObjectNameGraphic;
import pl.mmorpg.prototype.client.objects.graphic.GraphicGameObject;

public class ObjectInfoDisplayer extends ObjectGraphicEffectPlacer
{
	public ObjectInfoDisplayer(BlockingQueue<GraphicGameObject> clientGraphics)
	{
		super(clientGraphics);
	}

	@Override
	protected GraphicGameObject createEffect(GameObject gameObject, Supplier<Boolean> removalCondition)
	{
		return new GameObjectNameGraphic(gameObject, removalCondition);
	}
}
