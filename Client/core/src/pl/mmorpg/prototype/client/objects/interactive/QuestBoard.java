package pl.mmorpg.prototype.client.objects.interactive;

import pl.mmorpg.prototype.client.resources.Assets;

public class QuestBoard extends InteractiveGameObject
{
	public QuestBoard(long id)
	{
		super(Assets.get("Static enviroment/questBoard.png"), id);
		setSize(32, 50);
	}
}
