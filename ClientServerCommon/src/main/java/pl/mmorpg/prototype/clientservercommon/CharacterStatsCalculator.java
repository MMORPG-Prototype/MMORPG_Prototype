package pl.mmorpg.prototype.clientservercommon;

import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class CharacterStatsCalculator
{
	public static Integer getMaxHP(UserCharacterDataPacket character)
	{
		return getMaxHp(character.getLevel(), character.getStrength(), character.getMagic(), character.getDexitirity());
	}
	
	public static Integer getMaxHp(Integer level, Integer strength, Integer magic, Integer dexitirity)
	{
		return 100;
	}
	
	public static Integer getMaxMP(UserCharacterDataPacket character)
	{
		return getMaxHp(character.getLevel(), character.getStrength(), character.getMagic(), character.getDexitirity());
	}
	
	public static Integer getMaxMP(Integer level, Integer strength, Integer magic, Integer dexitirity)
	{
		return 100;
	}
}
