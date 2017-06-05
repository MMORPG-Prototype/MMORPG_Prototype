package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class BlueBerry extends StackableUseableItem
{

	public BlueBerry(long id)
	{
		this(id, 1);
	}
	
	public BlueBerry(long id, int count)
	{
		super(id, count);
	}

	@Override
	public void useItem(Monster target, PacketsSender packetSender)
	{
		//TODO use blue berry food
		
	}

}
