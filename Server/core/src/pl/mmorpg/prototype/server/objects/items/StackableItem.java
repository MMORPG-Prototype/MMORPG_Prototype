package pl.mmorpg.prototype.server.objects.items;

public abstract class StackableItem extends Item
{
    protected int count = 0;
    
    public StackableItem(long id)
    {
        super(id);
    }
    
    public StackableItem(long id, int count)
    {
        super(id);
        this.count = count;
    }
    
    public void addOneItemToStack()
    {
        count++;
    }
    
    public int getCount()
    {
        return count;
    }

	public void stackWith(StackableItem newItem)
	{
		this.count += newItem.count;
	}
}
