package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class CharacterMonsterTargetingPacket
{
	public int gameX;
	public int gameY;
}
