package pl.mmorpg.prototype.server.quests.events;

import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.npcs.Npc;

public class NpcDialogStartEvent extends NpcDialogEvent
{
	public NpcDialogStartEvent(Npc npc, PacketsSender packetsSender)
	{
		super(null, npc, packetsSender);
	}

}
