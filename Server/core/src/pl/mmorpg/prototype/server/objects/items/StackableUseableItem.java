package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.exceptions.OutOfStockException;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public abstract class StackableUseableItem extends StackableItem implements Useable
{
    public StackableUseableItem(long id, int count)
    {
        super(id, count);
    }
    
    public StackableUseableItem(long id)
    {
        super(id);
    }

    @Override
    public void use(Monster target, PacketsSender packetSender)
    {
        if(count > 0)
        {
            count--;
            useItem(target, packetSender);
            if(count <= 0)
            	target.removeItem(getId());
        }
        else
        	throw new OutOfStockException();
    }
    
    public abstract void useItem(Monster target, PacketsSender packetSender);

}
