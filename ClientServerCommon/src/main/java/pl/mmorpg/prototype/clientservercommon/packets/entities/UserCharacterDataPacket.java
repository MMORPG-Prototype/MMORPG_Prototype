package pl.mmorpg.prototype.clientservercommon.packets.entities;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class UserCharacterDataPacket
{
	public Integer id;
	public Integer level;
	public String nickname;
	public Integer experience;
	public Integer strength;
	public Integer magic;
	public Integer dexitirity;
}
