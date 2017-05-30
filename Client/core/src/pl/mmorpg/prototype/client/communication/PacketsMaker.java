package pl.mmorpg.prototype.client.communication;

import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemUseable;
import pl.mmorpg.prototype.client.objects.monsters.Monster;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.FireballSpellUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterTargetingPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.OpenContainterPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakeItemFromContainerPacket;

public class PacketsMaker
{

    public static ObjectRemovePacket makeRemovalPacket(int id)
    {
        return new ObjectRemovePacket(id);
    }

    public static ObjectRepositionPacket makeRepositionPacket(long id, float x, float y)
    {
        ObjectRepositionPacket packet = new ObjectRepositionPacket();
        packet.id = id;
        packet.x = x;
        packet.y = y;
        return packet;
    }

    public static ChatMessagePacket makeChatMessagePacket(String message)
    {
        ChatMessagePacket packet = new ChatMessagePacket();
        packet.setMessage(message);
        return packet;
    }

    public static MonsterTargetingPacket makeTargetingPacket(float x, float y)
    {
        MonsterTargetingPacket packet = new MonsterTargetingPacket();
        packet.gameX = (int) x;
        packet.gameY = (int) y;
        return packet;
    }

    public static <T extends Item & ItemUseable> ItemUsagePacket makeItemUsagePacket(T item, Monster target)
    {
        ItemUsagePacket packet = new ItemUsagePacket();
        packet.setItemId(item.getId());
        packet.setTargetId(target.getId());
        return packet;
    }

    public static FireballSpellUsagePacket makeFireballSpellUsagePacket()
    {
        FireballSpellUsagePacket packet = new FireballSpellUsagePacket();
        return packet;
    }

	public static OpenContainterPacket makeContainterOpeningPacket(float x, float y)
	{
		OpenContainterPacket packet = new OpenContainterPacket();
		packet.gameX = (int)x;
		packet.gameY = (int)y;
		return packet;
	}

	public static TakeItemFromContainerPacket makeTakeItemFromContainerPacket(long containerId, long itemId)
	{
		TakeItemFromContainerPacket packet = new TakeItemFromContainerPacket();
		packet.setContainerId(containerId);
		packet.setItemId(itemId);
		return packet;
	}
}
