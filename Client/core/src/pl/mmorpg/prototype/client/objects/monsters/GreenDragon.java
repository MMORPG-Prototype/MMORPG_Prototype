package pl.mmorpg.prototype.client.objects.monsters;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.GreenDragonPropertiesBuilder;

public class GreenDragon extends HealthBarMonster
{

	public GreenDragon(long id)
	{
		super(Assets.get("monsterSheet.png"), 0, 0, id, new GreenDragonPropertiesBuilder().build());
	}

}
