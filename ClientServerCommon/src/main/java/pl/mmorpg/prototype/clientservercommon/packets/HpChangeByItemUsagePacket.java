package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class HpChangeByItemUsagePacket
{
	private int hpChange;
	private long monsterTargetId;
}
