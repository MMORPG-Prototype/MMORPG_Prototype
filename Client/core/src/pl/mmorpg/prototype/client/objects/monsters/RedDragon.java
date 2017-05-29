package pl.mmorpg.prototype.client.objects.monsters;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.RedDragonPropertiesBuilder;

public class RedDragon extends HealthBarMonster
{
	public RedDragon(long id)
	{
		super(Assets.get("monsterSheet.png"), 9, 0, id, new RedDragonPropertiesBuilder().build());
	}


}
