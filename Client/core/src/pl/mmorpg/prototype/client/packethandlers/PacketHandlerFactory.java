package pl.mmorpg.prototype.client.packethandlers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.FrameworkMessage;

import pl.mmorpg.prototype.client.exceptions.UnknownPacketTypeException;
import pl.mmorpg.prototype.client.packethandlers.pure.ContainerGoldRemovalPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ContainerItemRemovalPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ExperienceGainPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.FireDamagePacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.HpChangeByItemUsagePacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.HpUpdatePacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.InventoryItemRepositionPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.InventoryItemStackPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.InventoryItemSwapPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ItemPutInQuickAccessBarPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ItemRewardRemovePacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ItemUsagePacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.KnownSpellInfoPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ManaDrainPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.MpChangeByItemUsagePacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.MpUpdatePacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.NormalDamagePacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.NpcContinueDialogPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.NpcStartDialogPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.NullPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ObjectRepositionPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.QuestAcceptedPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.QuestBoardInfoPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.QuestFinishedRewardPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.QuestRewardGoldRemovalPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.QuestStateInfoPacketArrayHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.RegisterationReplyPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ScriptExecutionErrorPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ScriptResultInfoPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.ShopItemsPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.SpellPutInQuickAccessBarPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.UnacceptableOperationPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.pure.UserCharacterDataArrayPacketHandler;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.HpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.HpUpdatePacket;
import pl.mmorpg.prototype.clientservercommon.packets.InventoryItemRepositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.InventoryItemStackPacket;
import pl.mmorpg.prototype.clientservercommon.packets.InventoryItemSwapPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.KnownSpellInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ManaDrainPacket;
import pl.mmorpg.prototype.clientservercommon.packets.MpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.MpUpdatePacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestAcceptedPacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestBoardInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestFinishedRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestStateInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptExecutionErrorPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptResultInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ShopItemsPacket;
import pl.mmorpg.prototype.clientservercommon.packets.damage.FireDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.damage.NormalDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ContainerGoldRemovalPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ContainerItemRemovalPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ExperienceGainPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemPutInQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemRewardRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.NpcContinueDialogPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.NpcStartDialogPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.QuestRewardGoldRemovalPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SpellPutInQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.UnacceptableOperationPacket;

public class PacketHandlerFactory
{
    private Map<Class<?>, PacketHandler<? extends Object>> packetHandlers = new HashMap<>();

    public PacketHandlerFactory(final PlayState playState, final StateManager states)
    { 
        packetHandlers.put(ObjectRepositionPacket.class, new ObjectRepositionPacketHandler(playState));
        packetHandlers.put(RegisterationReplyPacket.class, new RegisterationReplyPacketHandler(states));
        packetHandlers.put(UserCharacterDataPacket[].class, new UserCharacterDataArrayPacketHandler(states));
        packetHandlers.put(NormalDamagePacket.class, new NormalDamagePacketHandler(playState));
        packetHandlers.put(FireDamagePacket.class, new FireDamagePacketHandler(playState));
        packetHandlers.put(ExperienceGainPacket.class, new ExperienceGainPacketHandler(playState));
        packetHandlers.put(HpChangeByItemUsagePacket.class, new HpChangeByItemUsagePacketHandler(playState));
        packetHandlers.put(MpChangeByItemUsagePacket.class, new MpChangeByItemUsagePacketHandler(playState));
        packetHandlers.put(ManaDrainPacket.class, new ManaDrainPacketHandler(playState));
        packetHandlers.put(ItemUsagePacket.class, new ItemUsagePacketHandler(playState));
        packetHandlers.put(ContainerItemRemovalPacket.class, new ContainerItemRemovalPacketHandler(playState));
        packetHandlers.put(UnacceptableOperationPacket.class, new UnacceptableOperationPacketHandler(playState));
        packetHandlers.put(ContainerGoldRemovalPacket.class, new ContainerGoldRemovalPacketHandler(playState));
        packetHandlers.put(HpUpdatePacket.class, new HpUpdatePacketHandler(playState));
        packetHandlers.put(MpUpdatePacket.class, new MpUpdatePacketHandler(playState));
        packetHandlers.put(ShopItemsPacket.class, new ShopItemsPacketHandler(playState));
        packetHandlers.put(ScriptExecutionErrorPacket.class, new ScriptExecutionErrorPacketHandler(playState));
        packetHandlers.put(InventoryItemRepositionPacket.class, new InventoryItemRepositionPacketHandler(playState));
        packetHandlers.put(InventoryItemSwapPacket.class, new InventoryItemSwapPacketHandler(playState));
        packetHandlers.put(InventoryItemStackPacket.class, new InventoryItemStackPacketHandler(playState));
        packetHandlers.put(ItemPutInQuickAccessBarPacket.class, new ItemPutInQuickAccessBarPacketHandler(playState));
        packetHandlers.put(SpellPutInQuickAccessBarPacket.class, new SpellPutInQuickAccessBarPacketHandler(playState));
        packetHandlers.put(ScriptResultInfoPacket.class, new ScriptResultInfoPacketHandler(playState));
        packetHandlers.put(QuestBoardInfoPacket.class, new QuestBoardInfoPacketHandler(playState));
        packetHandlers.put(QuestFinishedRewardPacket.class, new QuestFinishedRewardPacketHandler(playState));
        packetHandlers.put(ItemRewardRemovePacket.class, new ItemRewardRemovePacketHandler(playState));
        packetHandlers.put(QuestRewardGoldRemovalPacket.class, new QuestRewardGoldRemovalPacketHandler(playState));
        packetHandlers.put(QuestStateInfoPacket[].class, new QuestStateInfoPacketArrayHandler(playState));
        packetHandlers.put(QuestAcceptedPacket.class, new QuestAcceptedPacketHandler(playState));
        packetHandlers.put(NpcStartDialogPacket.class, new NpcStartDialogPacketHandler(playState));
        packetHandlers.put(NpcContinueDialogPacket.class, new NpcContinueDialogPacketHandler(playState));
        packetHandlers.put(KnownSpellInfoPacket.class, new KnownSpellInfoPacketHandler(playState));
        
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
