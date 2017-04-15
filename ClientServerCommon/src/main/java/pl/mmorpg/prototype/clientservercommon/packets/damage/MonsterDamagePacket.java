package pl.mmorpg.prototype.clientservercommon.packets.damage;

import lombok.Data;

@Data
public abstract class MonsterDamagePacket
{
    private long targetId;
    private int damage;
}
