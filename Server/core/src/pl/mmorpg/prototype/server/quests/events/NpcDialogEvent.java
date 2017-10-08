package pl.mmorpg.prototype.server.quests.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.npcs.Npc;

@Getter
@AllArgsConstructor
public class NpcDialogEvent extends EventBase
{
	private final String anwser;
	private final Npc npc;
	private final PacketsSender packetsSender;
	
	public boolean isDialogStarting()
	{
		return anwser == null;
	}
}
