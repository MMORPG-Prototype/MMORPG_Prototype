package pl.mmorpg.prototype.server.states;

import java.util.Map;

import pl.mmorpg.prototype.server.objects.GameObject;

public interface GameObjectsContainer
{
	void add(GameObject object);

	GameObject getObject(long id);

	void remove(long objectId);

	Map<Long, GameObject> getGameObjects();
}
