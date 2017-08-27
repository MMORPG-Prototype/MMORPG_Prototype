package pl.mmorpg.prototype.server.objects.ineractivestaticobjects;

import pl.mmorpg.prototype.server.resources.Assets;

public class QuestBoard extends InteractiveStaticObject
{
	public QuestBoard(long id)
	{
		super(Assets.get("Static enviroment/questBoard.png"), id);
		setSize(32, 50);
	}
}
