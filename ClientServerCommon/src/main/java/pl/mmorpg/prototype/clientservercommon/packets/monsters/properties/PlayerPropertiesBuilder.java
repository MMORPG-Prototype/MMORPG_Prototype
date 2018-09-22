package pl.mmorpg.prototype.clientservercommon.packets.monsters.properties;

import pl.mmorpg.prototype.clientservercommon.CharacterStatsCalculator;
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
		 		attackPower(10)
				.attackRange(50)
				.attackSpeed(1.0f)
				.defense(5)
				.hp(dataPacket.getHitPoints())
				.mp(dataPacket.getManaPoints())
				.maxMp(CharacterStatsCalculator.getMaxMp(dataPacket))
		 		.maxHp(CharacterStatsCalculator.getMaxHP(dataPacket))
		 		.gold(dataPacket.getGold())
		 		.experience(dataPacket.getExperience())
		 		.level(dataPacket.getLevel())
		 		.dexitirity(dataPacket.getDexterity())
		 		.strength(dataPacket.getStrength())
		 		.intelligence(dataPacket.getIntelligence());
		 return super.build();
	}
}