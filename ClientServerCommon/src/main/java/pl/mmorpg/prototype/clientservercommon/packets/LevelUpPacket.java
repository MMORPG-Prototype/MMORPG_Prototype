package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class LevelUpPacket
{
	private long targetId;
	private int levelUpPoints;
}
