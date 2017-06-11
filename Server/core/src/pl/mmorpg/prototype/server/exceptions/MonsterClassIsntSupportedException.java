package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class MonsterClassIsntSupportedException extends GameException
{
	public MonsterClassIsntSupportedException(Class<? extends Monster> type)
	{
		super(type.getSimpleName());
	}
}
