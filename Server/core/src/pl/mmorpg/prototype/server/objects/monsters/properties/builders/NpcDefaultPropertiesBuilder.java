package pl.mmorpg.prototype.server.objects.monsters.properties.builders;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;

public class NpcDefaultPropertiesBuilder extends MonsterProperties.Builder
{
	@Override
	public MonsterProperties build()
	{
		experienceGain(10000)
				.strength(100)
				.level(10);
		return super.build();
	}
}
