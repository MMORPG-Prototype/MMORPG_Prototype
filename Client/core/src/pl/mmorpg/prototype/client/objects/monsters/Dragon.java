package pl.mmorpg.prototype.client.objects.monsters;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.monsterproperties.DragonPropertiesBuilder;

public class Dragon extends HealthBarMonster
{

	public Dragon(long id)
	{
		super(Assets.get("monsterSheet.png"), 0, 0, id, new DragonPropertiesBuilder().build());
	}

}
