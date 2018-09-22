package pl.mmorpg.prototype.clientservercommon;

import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class CharacterStatsCalculator
{
	public static Integer getMaxHP(UserCharacterDataPacket character)
	{
		return getMaxHp(character.getLevel(), character.getStrength(), character.getIntelligence(), character.getDexterity());
	}
	
	public static Integer getMaxHp(Integer level, Integer strength, Integer magic, Integer dexitirity)
	{
		return 100;
	}
	
	public static Integer getMaxMp(UserCharacterDataPacket character)
	{
		return getMaxMp(character.getLevel(), character.getStrength(), character.getIntelligence(), character.getDexterity());
	}
	
	public static Integer getMaxMp(Integer level, Integer strength, Integer magic, Integer dexitirity)
	{
		return 100;
	}
}
