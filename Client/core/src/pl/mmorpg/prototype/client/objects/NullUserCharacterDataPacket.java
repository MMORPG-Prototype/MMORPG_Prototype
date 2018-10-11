package pl.mmorpg.prototype.client.objects;

import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class NullUserCharacterDataPacket extends UserCharacterDataPacket
{
	public NullUserCharacterDataPacket()
	{
		setId(-1);
		setHitPoints(-1);
		setManaPoints(-1);
		setLevelUpPoints(-1);
		setLevel(-1);
		setDexterity(-1);
		setExperience(-1);
		setIntelligence(-1);
		setStrength(-1);
		setGold(-1);
		setNickname("");
		setStartingMap("");
		setStartingX(-1);
		setStartingY(-1);
	}
}
