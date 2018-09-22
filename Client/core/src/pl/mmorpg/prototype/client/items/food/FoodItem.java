package pl.mmorpg.prototype.client.items.food;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.items.ItemUseable;
import pl.mmorpg.prototype.client.items.StackableItem;
import pl.mmorpg.prototype.client.objects.monsters.Monster;

public abstract class FoodItem extends StackableItem implements ItemUseable
{
	public FoodItem(Texture texture, long id)
	{
		super(texture, id);
	}

	public FoodItem(Texture texture, long id, int itemCount)
	{
		super(texture, id, itemCount);
	}


	@Override
	public void use(Monster target, PacketsSender packetSender)
	{
		packetSender.send(PacketsMaker.makeItemUsagePacket(this, target));
	}

	@Override
	public void useInterfaceUpdate()
	{
		decreaseItemCount();
	}



}
