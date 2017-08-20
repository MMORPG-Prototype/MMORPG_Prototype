package pl.mmorpg.prototype.client.userinterface.dialogs.components;

public interface Container<T>
{
	void put(T content);

	boolean hasContent();

	T getContent();

	void removeContent();

}