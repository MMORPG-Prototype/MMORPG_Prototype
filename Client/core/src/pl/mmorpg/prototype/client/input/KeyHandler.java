package pl.mmorpg.prototype.client.input;

public interface KeyHandler
{
	public static final KeyHandler EMPTY = () -> {};

	void handle();
}
