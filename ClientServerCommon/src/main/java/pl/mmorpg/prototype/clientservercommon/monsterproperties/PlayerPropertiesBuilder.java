package pl.mmorpg.prototype.clientservercommon.monsterproperties;

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
				.maxHp(CharacterStatsCalculator.getMaxHP(dataPacket))
				.maxMp(CharacterStatsCalculator.getMaxMP(dataPacket));
		 return super.build();
	}
}