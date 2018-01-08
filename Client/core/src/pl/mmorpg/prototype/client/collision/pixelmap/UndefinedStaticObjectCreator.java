package pl.mmorpg.prototype.client.collision.pixelmap;

import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.client.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.clientservercommon.Identifiable;

public interface UndefinedStaticObjectCreator<T extends RectangleCollisionObject & Identifiable>
{
	T create(Rectangle rectangle, int id);
}
