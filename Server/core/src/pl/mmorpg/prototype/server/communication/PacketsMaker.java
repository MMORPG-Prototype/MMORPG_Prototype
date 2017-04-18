package pl.mmorpg.prototype.server.communication;

import java.util.Collection;

import pl.mmorpg.prototype.clientservercommon.packets.ContainerContentPacket;
import pl.mmorpg.prototype.clientservercommon.packets.HpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ManaDrainPacket;
import pl.mmorpg.prototype.clientservercommon.packets.MonsterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.MpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.PlayerCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.damage.FireDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.damage.NormalDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ExperienceGainPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterTargetingReplyPacket;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.items.StackableItem;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class PacketsMaker
{
    public static ObjectCreationPacket makeCreationPacket(GameObject object)
    {
        ObjectCreationPacket packet = new ObjectCreationPacket();
        packet.id = object.getId();
        packet.identifier = object.getIdentifier();
        packet.x = object.getX();
        packet.y = object.getY();
        return packet;
    }

    public static MonsterCreationPacket makeCreationPacket(Monster monster)
    {
        MonsterCreationPacket packet = new MonsterCreationPacket();
        packet.id = monster.getId();
        packet.identifier = monster.getIdentifier();
        packet.x = monster.getX();
        packet.y = monster.getY();
        packet.properties = monster.getProperties();
        return packet;
    }

    public static PlayerCreationPacket makeCreationPacket(PlayerCharacter player)
    {
        PlayerCreationPacket packet = new PlayerCreationPacket();
        packet.id = player.getId();
        packet.identifier = player.getIdentifier();
        packet.x = player.getX();
        packet.y = player.getY();
        packet.properties = player.getProperties();
        packet.data = PacketsMaker.makeCharacterPacket(player.getUserCharacterData());
        return packet;
    }

    public static ObjectRemovePacket makeRemovalPacket(long id)
    {
        return new ObjectRemovePacket(id);
    }

    public static ObjectRepositionPacket makeRepositionPacket(GameObject gameObject)
    {
        return makeRepositionPacket(gameObject.getId(), gameObject.getX(), gameObject.getY());
    }

    public static ObjectRepositionPacket makeRepositionPacket(long id, float x, float y)
    {
        ObjectRepositionPacket packet = new ObjectRepositionPacket();
        packet.id = id;
        packet.x = x;
        packet.y = y;
        return packet;
    }

    public static UserCharacterDataPacket makeCharacterPacket(UserCharacter character)
    {
        UserCharacterDataPacket packet = new UserCharacterDataPacket();
        packet.setId(character.getId());
        packet.setLevel(character.getLevel());
        packet.setHitPoints(character.getHitPoints());
        packet.setManaPoints(character.getManaPoints());
        packet.setNickname(character.getNickname());
        packet.setExperience(character.getExperience());
        packet.setStrength(character.getStrength());
        packet.setMagic(character.getMagic());
        packet.setDexitirity(character.getDexitirity());
        packet.setGold(character.getGold());
        packet.setStartingX(character.getLastLocationX());
        packet.setStartingY(character.getLastLocationY());
        return packet;
    }

    public static CharacterItemDataPacket makeItemPacket(CharacterItem item, long id)
    {
        CharacterItemDataPacket packet = new CharacterItemDataPacket();
        packet.setId(id);
        packet.setIdentifier(item.getIdentifier().toString());
        packet.setCount(item.getCount());
        return packet;
    }

    public static CharacterItemDataPacket makeItemPacket(Item item)
    {
        CharacterItemDataPacket packet = new CharacterItemDataPacket();
        packet.setId(item.getId());
        packet.setIdentifier(item.getIdentifier().toString());
        if (item instanceof StackableItem)
            packet.setCount(((StackableItem) item).getCount());
        return packet;
    }

    public static MonsterTargetingReplyPacket makeTargetingReplyPacket(GameObject target)
    {
        if (target == null)
            throw new NullPointerException("Target cannot be null");
        MonsterTargetingReplyPacket packet = new MonsterTargetingReplyPacket();
        packet.monsterId = target.getId();
        return packet;
    }

    public static NormalDamagePacket makeNormalDamagePacket(long id, int damage)
    {
        NormalDamagePacket packet = new NormalDamagePacket();
        packet.setTargetId(id);
        packet.setDamage(damage);
        return packet;
    }

    public static ExperienceGainPacket makeExperienceGainPacket(long id, int experienceGain)
    {
        ExperienceGainPacket packet = new ExperienceGainPacket();
        packet.setTargetId(id);
        packet.setExperience(experienceGain);
        return packet;
    }

    public static HpChangeByItemUsagePacket makeHpChangeByItemUsagePacket(int delta, long targetId)
    {
        HpChangeByItemUsagePacket packet = new HpChangeByItemUsagePacket();
        packet.setHpChange(delta);
        packet.setMonsterTargetId(targetId);
        return packet;
    }

    public static MpChangeByItemUsagePacket makeMpChangeByItemUsagePacket(int delta, long targetId)
    {
        MpChangeByItemUsagePacket packet = new MpChangeByItemUsagePacket();
        packet.setMpChange(delta);
        packet.setMonsterTargetId(targetId);
        return packet;
    }

    public static FireDamagePacket makeFireDamagePacket(long targetId, int spellDamage)
    {
        FireDamagePacket fireDamagePacket = new FireDamagePacket();
        fireDamagePacket.setTargetId(targetId);
        fireDamagePacket.setDamage(spellDamage);
        return fireDamagePacket;
    }

    public static ManaDrainPacket makeManaDrainPacket(int manaDrain)
    {
        ManaDrainPacket packet = new ManaDrainPacket();
        packet.manaDrained = manaDrain;
        return packet;
    }

	public static ContainerContentPacket makeOpenContainerPacket(Collection<Item> containerItems)
	{
		CharacterItemDataPacket[] containerContent = containerItems.stream()
				.map( item -> makeItemPacket(item))
				.toArray(CharacterItemDataPacket[]::new);
		ContainerContentPacket packet = new ContainerContentPacket();
		packet.setContentItems(containerContent);
		return packet;
	}


}
