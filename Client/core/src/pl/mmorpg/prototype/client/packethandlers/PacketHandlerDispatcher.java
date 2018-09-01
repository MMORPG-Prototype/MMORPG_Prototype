package pl.mmorpg.prototype.client.packethandlers;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import pl.mmorpg.prototype.client.exceptions.CannotCreatePacketHandlerException;
import pl.mmorpg.prototype.client.exceptions.GameException;
import pl.mmorpg.prototype.clientservercommon.packets.GameObjectTargetPacket;

public class PacketHandlerDispatcher
{
	private final Map<Class<?>, Collection<PacketHandler<Object>>> packetHandlers = new ConcurrentHashMap<>();

	public void registerHandler(PacketHandler<Object> packetHandler)
	{
		Class<?> packetType = getPacketType(packetHandler);
		Collection<PacketHandler<Object>> packetHandlerGroup = packetHandlers.get(packetType);
		if(packetHandlerGroup == null)
		{
			packetHandlerGroup = new LinkedBlockingDeque<>();
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
		try
		{
			if (packet instanceof GameObjectTargetPacket)
				dispatchGameObjectTargetPacket((GameObjectTargetPacket) packet);
			else
				dispatchNormalPacket(packet);
		} catch (GameException e)
		{
			System.err.println(e.getMessage());
		}
	}

	private void dispatchNormalPacket(Object packet)
	{
		Collection<PacketHandler<Object>> packetHandlerGroup = packetHandlers.get(packet.getClass());
		if (packetHandlerGroup == null)
			throw newOmittingPacketException(packet);
		packetHandlerGroup.forEach(handler -> handler.handle(packet));
	}

	private GameException newOmittingPacketException(Object packet)
	{
		return new GameException("Omitting packet handling: " + packet.getClass().getSimpleName() + ", no handler registered");
	}

	public void dispatchGameObjectTargetPacket(GameObjectTargetPacket packet)
	{
		GameObjectTargetPacketHandler<Object> packetHandler = findProperHandler(packet);
		packetHandler.handle(packet);
	}

	private GameObjectTargetPacketHandler<Object> findProperHandler(GameObjectTargetPacket packet)
	{
		Collection<PacketHandler<Object>> collection = packetHandlers.get(packet.getClass());
		if(collection == null)
			throw newOmittingPacketException(packet);

		for(PacketHandler<Object> packetHandler : collection)
			if(packetHandler instanceof GameObjectTargetPacketHandler && ((GameObjectTargetPacketHandler<Object>) packetHandler).getObjectId() == packet.getTargetId())
				return (GameObjectTargetPacketHandler<Object>)packetHandler;
		throw newOmittingPacketException(packet);
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
			.flatMap(Collection::stream)
			.forEach(PacketHandler::tryHandlingUnhandledPackets);
	}

	public void registerPrivateClassPacketHandlers(Object objectContainingDefinitionOfPrivatePacketHandlers)
	{
		Class<?>[] declaredClasses = objectContainingDefinitionOfPrivatePacketHandlers.getClass().getDeclaredClasses();
		Arrays.stream(declaredClasses)
			.filter(this::isPacketHandlerClass)
			.map(packetHandlerClass -> tryCreatingInstance(packetHandlerClass,
					objectContainingDefinitionOfPrivatePacketHandlers))
			.forEach(this::registerHandler);
	}


	private PacketHandler<Object> tryCreatingInstance(Class<?> packetHandlerClass,
			Object objectContainingDefinitionOfPrivatePacketHandlers)
	{
		try
		{
			Constructor<?> packetHandlerConstructor = packetHandlerClass
					.getDeclaredConstructor(objectContainingDefinitionOfPrivatePacketHandlers.getClass());
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
