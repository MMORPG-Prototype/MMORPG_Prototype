package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.server.objects.monsters.Monster;

public abstract class UseableItem extends Item implements Useable
{

    public UseableItem(long id)
    {
        super(id);
    }

}
