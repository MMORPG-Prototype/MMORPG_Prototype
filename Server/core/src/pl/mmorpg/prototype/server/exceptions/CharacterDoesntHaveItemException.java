package pl.mmorpg.prototype.server.exceptions;

public class CharacterDoesntHaveItemException extends GameException
{

    public CharacterDoesntHaveItemException(long id)
    {
        super("Item id: " + id);
    }

}
