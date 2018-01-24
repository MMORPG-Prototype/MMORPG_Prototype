package pl.mmorpg.prototype.client.packethandlers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.FrameworkMessage;

import pl.mmorpg.prototype.client.exceptions.UnknownPacketTypeException;
import pl.mmorpg.prototype.client.packethandlers.pure.NullPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.QuestAcceptedPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.QuestBoardInfoPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.QuestFinishedRewardPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.QuestRewardGoldRemovalPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.QuestStateInfoPacketArrayHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ScriptExecutionErrorPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ScriptResultInfoPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ShopItemsPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.SpellPutInQuickAccessBarPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.UnacceptableOperationPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.UserCharacterDataArrayPacketHandler;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.QuestAcceptedPacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestBoardInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestFinishedRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestStateInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptExecutionErrorPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptResultInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ShopItemsPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.QuestRewardGoldRemovalPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SpellPutInQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.UnacceptableOperationPacket;

public class PacketHandlerFactory
{
    private Map<Class<?>, PacketHandler<? extends Object>> packetHandlers = new HashMap<>();

    public PacketHandlerFactory(final PlayState playState, final StateManager states)
    { 
        packetHandlers.put(UserCharacterDataPacket[].class, new UserCharacterDataArrayPacketHandler(states));
        packetHandlers.put(UnacceptableOperationPacket.class, new UnacceptableOperationPacketHandler(playState));
        packetHandlers.put(ShopItemsPacket.class, new ShopItemsPacketHandler(playState));
        packetHandlers.put(ScriptExecutionErrorPacket.class, new ScriptExecutionErrorPacketHandler(playState));
        packetHandlers.put(SpellPutInQuickAccessBarPacket.class, new SpellPutInQuickAccessBarPacketHandler(playState));
        packetHandlers.put(ScriptResultInfoPacket.class, new ScriptResultInfoPacketHandler(playState));
        packetHandlers.put(QuestBoardInfoPacket.class, new QuestBoardInfoPacketHandler(playState));
        packetHandlers.put(QuestFinishedRewardPacket.class, new QuestFinishedRewardPacketHandler(playState));
        packetHandlers.put(QuestRewardGoldRemovalPacket.class, new QuestRewardGoldRemovalPacketHandler(playState));
        packetHandlers.put(QuestStateInfoPacket[].class, new QuestStateInfoPacketArrayHandler(playState));
        packetHandlers.put(QuestAcceptedPacket.class, new QuestAcceptedPacketHandler(playState));
        
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
