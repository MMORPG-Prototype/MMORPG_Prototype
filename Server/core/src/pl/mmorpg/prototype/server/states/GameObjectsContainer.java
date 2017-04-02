package pl.mmorpg.prototype.server.states;

import java.util.Map;

import pl.mmorpg.prototype.server.objects.GameObject;

public interface GameObjectsContainer
{
	void add(GameObject object);

	GameObject getObject(long id);

	GameObject remove(long objectId);

	boolean has(long objectId);

	Map<Long, GameObject> getGameObjects();
}
