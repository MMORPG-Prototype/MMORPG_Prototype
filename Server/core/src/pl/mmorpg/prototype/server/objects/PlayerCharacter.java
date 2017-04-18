package pl.mmorpg.prototype.server.objects;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.PlayerPropertiesBuilder;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.exceptions.CannotUseThisItemException;
import pl.mmorpg.prototype.server.exceptions.CharacterDoesntHaveItemException;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.items.Useable;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class PlayerCharacter extends Monster
{
    private UserCharacter userCharacter;
    private Map<Long, Item> items = new ConcurrentHashMap<>();

    public PlayerCharacter(UserCharacter userCharacter, PlayState linkedState)
    {
        super(Assets.get("MainChar.png"), userCharacter.getId(), linkedState,
                new PlayerPropertiesBuilder(PacketsMaker.makeCharacterPacket(userCharacter)).build());
        this.userCharacter = userCharacter;
        setPacketSendingInterval(0.0f);
        setPosition(userCharacter.getLastLocationX(), userCharacter.getLastLocationY());
    }

    @Override
    public void killed(Monster target)
    {
        linkedState.playerKilled(this, target);
        userCharacter.setExperience(userCharacter.getExperience() + target.getProperties().experienceGain);
        super.killed(target);
    }

    public UserCharacter getUserCharacterData()
    {
        return userCharacter;
    }

    public void addItem(Item item)
    {
        items.put(item.getId(), item);
    }

    public void useItem(long id, PacketsSender packetSender)
    {
        useItem(id, this, packetSender);
    }

    public void useItem(long id, Monster target, PacketsSender packetSender)
    {
        Item characterItem = items.get(id);
        if (characterItem == null)
            throw new CharacterDoesntHaveItemException(id);
        if (!(characterItem instanceof Useable))
            throw new CannotUseThisItemException(characterItem);
        ((Useable) characterItem).use(target, packetSender);
    }

    public Collection<Item> getItems()
    {
        return items.values();
    }
    
    public void spellUsed(int manaDrain)
    {
        int newManaValue = userCharacter.getManaPoints() - manaDrain;
        userCharacter.setManaPoints(newManaValue);
        getProperties().mp = newManaValue;
    }

    public boolean hasMana(int manaDrain)
    {
        return getProperties().mp >= manaDrain;
    }

	public void updateUserCharacterData()
	{
		userCharacter.setDexitirity(properties.dexitirity);
		userCharacter.setExperience(properties.experience);
		userCharacter.setGold(properties.gold);
		userCharacter.setHitPoints(properties.hp);
		userCharacter.setLastLocationX((int)getX());
		userCharacter.setLastLocationY((int)getY());
		userCharacter.setLevel(properties.level);
		userCharacter.setMagic(properties.magic);
		userCharacter.setManaPoints(properties.mp);
		userCharacter.setStrength(properties.strength);
	}
}
