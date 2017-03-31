package pl.mmorpg.prototype.server.input;

public interface KeyHandler
{
	public static final KeyHandler EMPTY = () -> {};

	void handle();
}
