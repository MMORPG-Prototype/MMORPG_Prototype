package pl.mmorpg.prototype.client.packethandlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class PacketHandlerBase<T extends Object> implements PacketHandler<T>
{
	private final Collection<T> packetsToHandle = new ArrayList<>();

	@Override
	public void handle(T packet)
	{
		if (canBeHandled(packet))
			doHandle(packet);
		else if (!canBeOmmited(packet))
			packetsToHandle.add(packet);
	}

	protected abstract void doHandle(T packet);

	@Override
	public void tryHandlingUnhandledPackets()
	{
		Iterator<T> it = packetsToHandle.iterator();
		while (it.hasNext())
		{
			T packet = it.next();
			if (canBeHandled(packet))
			{
				doHandle(packet);
				it.remove();
			} else if (canBeOmmited(packet))
				it.remove();
		}
	}

	@Override
	public boolean canBeHandled(Object packet)
	{
		return true;
	}

	@Override
	public boolean canBeOmmited(Object packet)
	{
		return false;
	}
}
