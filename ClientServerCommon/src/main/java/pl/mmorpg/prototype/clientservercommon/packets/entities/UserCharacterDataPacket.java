package pl.mmorpg.prototype.clientservercommon.packets.entities;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class UserCharacterDataPacket
{
	private Integer id;
	private Integer level;
	private Integer hitPoints;
	private Integer manaPoints;
	private Integer gold;
	private String nickname;
	private Integer experience;
	private Integer strength;
	private Integer magic;
	private Integer levelUpPoints;
	private Integer dexterity;
	private Integer startingX;
	private Integer startingY;
	private String startingMap;
}
