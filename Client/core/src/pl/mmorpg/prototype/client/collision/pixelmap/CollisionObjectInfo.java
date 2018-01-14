package pl.mmorpg.prototype.client.collision.pixelmap;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.mmorpg.prototype.client.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.clientservercommon.Identifiable;

@Data
@RequiredArgsConstructor
public class CollisionObjectInfo<T extends RectangleCollisionObject & Identifiable>
{
	private final T object;
	private boolean onCollisionMap = false;
}
