package pl.mmorpg.prototype.server.objects.items.potions;

import pl.mmorpg.prototype.server.objects.items.StackableUseableItem;

public abstract class Potion extends StackableUseableItem
{
    public Potion(long id, int count)
    {
        super(id, count);
    }

    public Potion(long id)
    {
        super(id);
    }

}
