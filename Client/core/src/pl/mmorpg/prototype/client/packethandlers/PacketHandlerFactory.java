package pl.mmorpg.prototype.client.packethandlers;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.FrameworkMessage;

import pl.mmorpg.prototype.client.exceptions.UnknownPacketTypeException;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessageReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ContainerContentPacket;
import pl.mmorpg.prototype.clientservercommon.packets.GoldAmountChangePacket;
import pl.mmorpg.prototype.clientservercommon.packets.GoldReceivePacket;
import pl.mmorpg.prototype.clientservercommon.packets.HpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.HpUpdatePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ManaDrainPacket;
import pl.mmorpg.prototype.clientservercommon.packets.MonsterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.MpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.MpUpdatePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.PlayerCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptExecutionErrorPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ShopItemsPacket;
import pl.mmorpg.prototype.clientservercommon.packets.damage.FireDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.damage.NormalDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ContainerGoldRemovalPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ContainerItemRemovalPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ExperienceGainPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterTargetingReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.UnacceptableOperationPacket;

public class PacketHandlerFactory
{
    private Map<Class<?>, PacketHandler> packetHandlers = new HashMap<>();

    public PacketHandlerFactory(final PlayState playState, final StateManager states)
    {
        packetHandlers.put(AuthenticationReplyPacket.class, new AuthenticationReplyPacketHandler(states));
        packetHandlers.put(CharacterCreationReplyPacket.class, new CharacterCreationReplyPacketHandler(states));
        packetHandlers.put(CharacterItemDataPacket.class, new CharacterItemDataPacketHandler(playState));
        packetHandlers.put(ObjectCreationPacket.class, new ObjectCreationPacketHandler(playState));
        packetHandlers.put(ObjectRemovePacket.class, new ObjectRemovePacketHandler(playState));
        packetHandlers.put(ObjectRepositionPacket.class, new ObjectRepositionPacketHandler(playState));
        packetHandlers.put(RegisterationReplyPacket.class, new RegisterationReplyPacketHandler(states));
        packetHandlers.put(UserCharacterDataPacket[].class, new UserCharacterDataArrayPacketHandler(states));
        packetHandlers.put(ChatMessageReplyPacket.class, new ChatMessageReplyPacketHandler(playState));
        packetHandlers.put(MonsterTargetingReplyPacket.class,
                new CharacterMonsterTargetingReplyPacketHandler(playState));
        packetHandlers.put(NormalDamagePacket.class, new NormalDamagePacketHandler(playState));
        packetHandlers.put(FireDamagePacket.class, new FireDamagePacketHandler(playState));
        packetHandlers.put(MonsterCreationPacket.class, new MonsterCreationPacketHandler(playState));
        packetHandlers.put(ExperienceGainPacket.class, new ExperienceGainPacketHandler(playState));
        packetHandlers.put(PlayerCreationPacket.class, new PlayerCreationPacketHandler(playState));
        packetHandlers.put(HpChangeByItemUsagePacket.class, new HpChangeByItemUsagePacketHandler(playState));
        packetHandlers.put(MpChangeByItemUsagePacket.class, new MpChangeByItemUsagePacketHandler(playState));
        packetHandlers.put(ManaDrainPacket.class, new ManaDrainPacketHandler(playState));
        packetHandlers.put(ItemUsagePacket.class, new ItemUsagePacketHandler(playState));
        packetHandlers.put(ContainerContentPacket.class, new ContainerContentPacketHandler(playState));
        packetHandlers.put(ContainerItemRemovalPacket.class, new ContainerItemRemovalPacketHandler(playState));
        packetHandlers.put(UnacceptableOperationPacket.class, new UnacceptableOperationPacketHandler(playState));
        packetHandlers.put(GoldReceivePacket.class, new GoldReceivePacketHandler(playState));
        packetHandlers.put(ContainerGoldRemovalPacket.class, new ContainerGoldRemovalPacketHandler(playState));
        packetHandlers.put(HpUpdatePacket.class, new HpUpdatePacketHandler(playState));
        packetHandlers.put(MpUpdatePacket.class, new MpUpdatePacketHandler(playState));
        packetHandlers.put(ShopItemsPacket.class, new ShopItemsPacketHandler(playState));
        packetHandlers.put(GoldAmountChangePacket.class, new GoldAmountChangePacketHandler(playState));
        packetHandlers.put(ScriptExecutionErrorPacket.class, new ScriptExecutionErrorPacketHandler(playState));

        // Ignore frameowrk keepAliveMessage
        packetHandlers.put(FrameworkMessage.KeepAlive.class, new NullPacketHandler());
    }

    public PacketHandler produce(Object object)
    {
        PacketHandler packetHandler = packetHandlers.get(object.getClass());
        if (packetHandler == null)
            throw new UnknownPacketTypeException(object);
        return packetHandler;
    }
}
