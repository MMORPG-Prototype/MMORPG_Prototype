package pl.mmorpg.prototype.server.objects.monsters;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.monsters.bodies.MonsterBody;
import pl.mmorpg.prototype.server.objects.monsters.loot.MonsterLootGenerator;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class LootableMonster extends AutoTargetingMonster
{
	private PlayState playState;

	protected LootableMonster(Texture lookout, long id, MonsterProperties properties,
			CollisionMap<GameObject> collisionMap, PlayState playState, MonsterLootGenerator lootGenerator)
	{
		super(lookout, id, properties, collisionMap, playState);
		this.playState = playState;
		lootGenerator.generateAndApplyLoot(this);
	}
	
	@Override
	public void onRemoval()
	{
		super.onRemoval();
		MonsterBody deadBody = getDeadBody();
		deadBody.setPosition(getX(), getY());
		playState.addDeadBody(deadBody);
		playState.sendToAll(PacketsMaker.makeCreationPacket(deadBody));
	}
	
	protected abstract MonsterBody getDeadBody();

}
