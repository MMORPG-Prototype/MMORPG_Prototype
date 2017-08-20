package pl.mmorpg.prototype.client.userinterface.dialogs.components;

public interface Container<T>
{
	void put(T item);

	boolean hasItem();

	T getItem();

	void removeItem();

}