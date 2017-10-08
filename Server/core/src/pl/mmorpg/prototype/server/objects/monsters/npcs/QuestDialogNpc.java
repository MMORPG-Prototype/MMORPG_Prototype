package pl.mmorpg.prototype.server.objects.monsters.npcs;

import pl.mmorpg.prototype.clientservercommon.NpcNames;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.states.PlayState;

public class QuestDialogNpc extends Npc
{

	public QuestDialogNpc(long id, PixelCollisionMap<GameObject> collisionMap, PlayState playState)
	{
		super(NpcNames.QUEST_DIALOG_NPC, id, collisionMap, playState);
	}

}
