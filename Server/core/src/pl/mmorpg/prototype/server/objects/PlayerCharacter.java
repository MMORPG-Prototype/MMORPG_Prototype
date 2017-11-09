package pl.mmorpg.prototype.server.objects;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.PlayerPropertiesBuilder;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.QuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.InventoryRepositionableItemsOwner;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class PlayerCharacter extends Monster implements InventoryRepositionableItemsOwner
{
    private UserCharacter userCharacter;
    private final int connectionId;

    public PlayerCharacter(UserCharacter userCharacter, PlayState linkedState, int connectionId)
    {
        super(Assets.get("MainChar.png"), userCharacter.getId(), linkedState,
                new PlayerPropertiesBuilder(PacketsMaker.makeCharacterPacket(userCharacter)).build());
        this.userCharacter = userCharacter;
        this.connectionId = connectionId;
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
    
    public void spellUsed(int manaDrain)
    {
        int newManaValue = getProperties().mp - manaDrain;
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

	public void addGold(int gold)
	{
		getProperties().gold += gold;
		userCharacter.setGold(userCharacter.getGold() + gold);
	}
	
	public void setGold(int gold)
	{
		getProperties().gold = gold;
		userCharacter.setGold(gold);
	}

	@Override
	public Item getItem(InventoryPosition position)
	{
		//Linear search, may need to improve
		return getItems().stream()
				.filter( item -> item.getInventoryPosition().equals(position))
				.findAny().orElse(null);
	}

	@Override
	public boolean hasItemInPosition(InventoryPosition position)
	{
		return getItem(position) != null;
	}

	public void putNewConfigElemetInQuickAccessBar(QuickAccessBarConfigurationElement quickAccessConfigElement)
	{
		userCharacter.getQuickAccessBarConfig().put(quickAccessConfigElement.getFieldPosition(), quickAccessConfigElement);
	}
	
	public void removeConfigElementFromQuickAccessBar(int cellPosition)
	{
		userCharacter.getQuickAccessBarConfig().remove(cellPosition);
	}

    public int getConnectionId()
    {
        return connectionId;
    }
	 
}
