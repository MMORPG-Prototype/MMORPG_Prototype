package pl.mmorpg.prototype.server.objects;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.PlayerPropertiesBuilder;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
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
    protected void killed(Monster target)
    {
        linkedState.playerKilled(this, target);
        userCharacter.setExperience(userCharacter.getExperience() + target.getProperites().experienceGain);
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
    
    public void useItem(long id)
    {
        useItem(id, this);
    }
    
    public void useItem(long id, Monster target)
    {
    	Item characterItem = items.get(id);
        if(characterItem == null)
            throw new CharacterDoesntHaveItemException(id);
        if(!(characterItem instanceof Useable))
            throw new CannotUseThisItemException(characterItem);
        ((Useable)characterItem).use(target);
    }

	public Collection<Item> getItems()
	{
		return items.values();		
	}
}
