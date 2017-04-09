package pl.mmorpg.prototype.client.objects.monsters;

import pl.mmorpg.prototype.client.objects.WalkingGameObject;
import pl.mmorpg.prototype.client.resources.Assets;

public class Dragon extends WalkingGameObject
{

	public Dragon(long id)
	{
		super(Assets.get("monsterSheet.png"), 0, 0, id);
	}

}
