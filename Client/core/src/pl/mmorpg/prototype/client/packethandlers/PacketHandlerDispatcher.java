package pl.mmorpg.prototype.client.packethandlers;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pl.mmorpg.prototype.client.exceptions.CannotCreatePacketHandlerException;
import pl.mmorpg.prototype.client.exceptions.GameException;

public class PacketHandlerDispatcher
{
	private final Map<Class<? extends Object>, Collection<PacketHandler<Object>>> packetHandlers = new ConcurrentHashMap<>();

	public void registerHandler(PacketHandler<Object> packetHandler)
	{
		Class<?> packetType = getPacketType(packetHandler);
		Collection<PacketHandler<Object>> packetHandlerGroup = packetHandlers.get(packetType);
		if(packetHandlerGroup == null)
		{
			packetHandlerGroup = new ArrayList<>();
			packetHandlers.put(packetType, packetHandlerGroup);
		}
		packetHandlerGroup.add(packetHandler);
	}

	private Class<?> getPacketType(PacketHandler<Object> packetHandler)
	{
		Class<?> packetHandlerClass = packetHandler.getClass();
		Type[] genericInterfaces = packetHandlerClass.getGenericInterfaces();
		for (Type genericInterface : genericInterfaces)
			if (genericInterface instanceof ParameterizedType
					&& ((ParameterizedType) genericInterface).getRawType() == PacketHandler.class)
				return (Class<?>) ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
		
		Type genericInterface = packetHandlerClass.getGenericSuperclass();
		return (Class<?>)((ParameterizedType) genericInterface).getActualTypeArguments()[0];
	}

	public void dispatchPacket(Object packet)
	{
		packetHandlers.get(packet.getClass()).forEach(handler -> handler.handle(packet));
	}
	
	public void removeHandler(PacketHandler<Object> packetHandler)
	{
		Class<?> packetType = getPacketType(packetHandler);
		Collection<PacketHandler<Object>> packetHandlerGroup = packetHandlers.get(packetType);
		if(packetHandlerGroup == null)
			throw new GameException("There is no such packet handler");
		packetHandlerGroup.removeIf(existingHandler -> existingHandler == packetHandler);
		if(packetHandlerGroup.isEmpty())
			packetHandlers.remove(packetType);
	}
	
	public void tryHandlingUnhandledPackets()
	{
		packetHandlers.values()
			.stream()
			.flatMap(a -> a.stream())
			.forEach(PacketHandler::tryHandlingUnhandledPackets);
	}

	public void registerPrivateClassPacketHandlers(Object objectContainingDefinitionOfPrivatePacketHandlers)
	{
		Class<?>[] declaredClasses = objectContainingDefinitionOfPrivatePacketHandlers.getClass().getDeclaredClasses();
		Arrays.stream(declaredClasses)
			.filter(this::isPacketHandlerClass)
			.map(packethandlerClass -> tryCreatingInstance(packethandlerClass, objectContainingDefinitionOfPrivatePacketHandlers))
			.forEach(this::registerHandler);
	}

	
	private PacketHandler<Object> tryCreatingInstance(Class<?> packetHandlerClass,
			Object objectContainingDefinitionOfPrivatePacketHandlers)
	{
		try
		{
			Constructor<?> packetHandlerConstructor = packetHandlerClass
					.getDeclaredConstructor(new Class[] { objectContainingDefinitionOfPrivatePacketHandlers.getClass() });
			packetHandlerConstructor.setAccessible(true);
			@SuppressWarnings("unchecked")
			PacketHandler<Object> packetHandler = (PacketHandler<Object>) packetHandlerConstructor.newInstance(new Object[] { objectContainingDefinitionOfPrivatePacketHandlers });
			return packetHandler;

		} catch (Exception e)
		{
			throw new CannotCreatePacketHandlerException(e.getMessage());
		}
	}

	private boolean isPacketHandlerClass(Class<?> packetHandlerCandidate)
	{
		return PacketHandler.class.isAssignableFrom(packetHandlerCandidate);
	}
}
