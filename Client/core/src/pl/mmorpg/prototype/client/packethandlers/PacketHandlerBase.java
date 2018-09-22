package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.exceptions.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class PacketHandlerBase<T extends Object> implements PacketHandler<T>
{
	private final Collection<T> packetsToHandle = new ArrayList<>();

	@Override
	public void handle(T packet)
	{
		ExceptionHandler.handle(() -> {
			if (canBeHandled(packet))
				doHandle(packet);
			else if (!canBeOmitted(packet))
				packetsToHandle.add(packet);
		});
	}

	protected abstract void doHandle(T packet);

	@Override
	public void tryHandlingUnhandledPackets()
	{
		ExceptionHandler.handle(() -> {
			Iterator<T> it = packetsToHandle.iterator();
			while (it.hasNext())
			{
				T packet = it.next();
				if (canBeHandled(packet))
				{
					doHandle(packet);
					it.remove();
				} else if (canBeOmitted(packet))
					it.remove();
			}
		});
	}

	@Override
	public boolean canBeHandled(T packet)
	{
		return true;
	}

	@Override
	public boolean canBeOmitted(T packet)
	{
		return false;
	}
}
