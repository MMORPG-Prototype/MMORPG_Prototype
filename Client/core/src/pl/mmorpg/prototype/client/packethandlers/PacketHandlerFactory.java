package pl.mmorpg.prototype.client.packethandlers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.FrameworkMessage;

import pl.mmorpg.prototype.client.exceptions.UnknownPacketTypeException;
import pl.mmorpg.prototype.client.packethandlers.pure.NullPacketHandler;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.StateManager;

@Deprecated
public class PacketHandlerFactory
{
    private Map<Class<?>, PacketHandler<? extends Object>> packetHandlers = new HashMap<>();

    public PacketHandlerFactory(final PlayState playState, final StateManager states)
    { 
        
        // Ignore framework keepAliveMessage
        packetHandlers.put(FrameworkMessage.KeepAlive.class, new NullPacketHandler());
    }
    
    public Collection<PacketHandler<? extends Object>> produceAll()
    {
		return packetHandlers.values();
    }

    public PacketHandler<? extends Object> produce(Object object)
    {
		PacketHandler<? extends Object> packetHandler = packetHandlers.get(object.getClass());
        if (packetHandler == null)
            throw new UnknownPacketTypeException(object);
        return packetHandler;
    }
}
