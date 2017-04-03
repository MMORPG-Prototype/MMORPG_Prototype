package pl.mmorpg.prototype.client.states.helpers;

import java.util.Map;

import pl.mmorpg.prototype.client.objects.GameObject;

public interface GameObjectsContainer
{
	void add(GameObject object);

	public GameObject getObject(long id);

	void removeObject(long id);

	Map<Long, GameObject> getGameObjects();
}
