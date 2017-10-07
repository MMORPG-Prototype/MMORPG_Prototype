package pl.mmorpg.prototype.client.objects.monsters.npcs;

import pl.mmorpg.prototype.client.objects.monsters.Monster;
import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.NpcDefaultPropertiesBuilder;

public abstract class Npc extends Monster
{
	protected Npc(TextureSheetAnimationInfo sheetInfo, long id)
	{
		super(sheetInfo, id, new NpcDefaultPropertiesBuilder().build());
	}

}
