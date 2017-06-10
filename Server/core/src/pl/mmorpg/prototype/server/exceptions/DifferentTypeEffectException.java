package pl.mmorpg.prototype.server.exceptions;

public class DifferentTypeEffectException extends GameException
{
	public DifferentTypeEffectException()
	{
		super("Effects should be same type when stacking");
	}
}	
