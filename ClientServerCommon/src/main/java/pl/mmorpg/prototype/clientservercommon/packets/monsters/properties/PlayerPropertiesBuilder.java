package pl.mmorpg.prototype.clientservercommon.packets.monsters.properties;

import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class PlayerPropertiesBuilder extends MonsterProperties.Builder
{
	private UserCharacterDataPacket dataPacket;

	public PlayerPropertiesBuilder(UserCharacterDataPacket dataPacket)
	{
		this.dataPacket = dataPacket;
	}
	
	@Override
	public MonsterProperties build()
	{
		hp(dataPacket.getHitPoints())
		.mp(dataPacket.getManaPoints())
		.gold(dataPacket.getGold())
		.experience(dataPacket.getExperience())
		.level(dataPacket.getLevel())
		.dexterity(dataPacket.getDexterity())
		.strength(dataPacket.getStrength())
		.intelligence(dataPacket.getIntelligence());
		 return super.build();
	}
}