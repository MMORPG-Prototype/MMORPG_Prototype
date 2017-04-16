package pl.mmorpg.prototype.clientservercommon.packets.damage;

abstract class MonsterDamagePacket
{
    private long targetId;
    private int damage;
    
    public long getTargetId()
    {
        return targetId;
    }
    
    public void setTargetId(long targetId)
    {
        this.targetId = targetId;
    }
    
    public int getDamage()
    {
        return damage;
    }
    
    public void setDamage(int damage)
    {
        this.damage = damage;
    }
}
