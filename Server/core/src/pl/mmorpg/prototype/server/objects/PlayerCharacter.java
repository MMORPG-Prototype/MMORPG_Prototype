package pl.mmorpg.prototype.server.objects;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.PlayerPropertiesBuilder;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.database.entities.CharacterSpell;
import pl.mmorpg.prototype.server.database.entities.ItemQuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.server.database.entities.SpellQuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.InventoryRepositionableItemsOwner;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.spells.Spell;
import pl.mmorpg.prototype.server.objects.monsters.spells.SpellFactory;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class PlayerCharacter extends Monster implements InventoryRepositionableItemsOwner
{
    private Character userCharacter;
    private final int connectionId;
    private final Map<Class<? extends Spell>, Spell> knownSpells = new HashMap<>();

    public PlayerCharacter(Character userCharacter, PlayState linkedState, int connectionId)
    {
        super(Assets.get("MainChar.png"), userCharacter.getId(), linkedState,
                new PlayerPropertiesBuilder(PacketsMaker.makeCharacterPacket(userCharacter)).build());
        this.userCharacter = userCharacter;
        this.connectionId = connectionId;
        initializeSpells(userCharacter.getSpells());
        setPacketSendingInterval(0.0f);
        setPosition(userCharacter.getLastLocationX(), userCharacter.getLastLocationY());
    }

    private void initializeSpells(Collection<CharacterSpell> spells)
	{
    	spells.stream()
    		.map(SpellFactory::create)
    		.forEach(spell -> knownSpells.put(spell.getClass(), spell));
	}
 
	@Override
    public void killed(Monster target)
    {
        linkedState.playerKilledMonster(this, target);
        super.killed(target);
    }

    public Character getUserCharacterData()
    {
        return userCharacter;
    }
    
    public void spellUsed(Spell spell)
    {
        int newManaValue = getProperties().mp - spell.getNeededMana();
        userCharacter.setManaPoints(newManaValue);
        getProperties().mp = newManaValue;
    }

    public boolean hasMana(int manaDrain)
    {
        return getProperties().mp >= manaDrain;
    }

	public void updateUserCharacterProperties()
	{
		userCharacter.setDexterity(properties.dexterity);
		userCharacter.setExperience(properties.experience);
		userCharacter.setGold(properties.gold);
		userCharacter.setHitPoints(properties.hp);
		userCharacter.setLastLocationX((int)getX());
		userCharacter.setLastLocationY((int)getY());
		userCharacter.setLevel(properties.level);
		userCharacter.setIntelligence(properties.intelligence);
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

	public void addExperience(int experience)
	{
		getProperties().experience += experience;
		userCharacter.setExperience(userCharacter.getExperience() + experience);
	}

	public void addLevel(int levelUpPoints)
	{
		getProperties().level += 1;
		userCharacter.setLevel(userCharacter.getLevel() + 1);
		userCharacter.setLevelUpPoints(userCharacter.getLevelUpPoints() + levelUpPoints);
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

	public void putNewConfigElementInItemQuickAccessBar(ItemQuickAccessBarConfigurationElement quickAccessConfigElement)
	{
		userCharacter.getItemQuickAccessBarConfig().put(quickAccessConfigElement.getFieldPosition(), quickAccessConfigElement);
	}
	
	public void removeConfigElementFromItemQuickAccessBar(int cellPosition)
	{
		userCharacter.getItemQuickAccessBarConfig().remove(cellPosition);
	}
	
	public void putNewConfigElementInSpellQuickAccessBar(
			SpellQuickAccessBarConfigurationElement quickAccessConfigElement)
	{
		userCharacter.getSpellQuickAccessBarConfig().put(quickAccessConfigElement.getFieldPosition(), quickAccessConfigElement);
	}
	
	public void removeConfigElementFromSpellQuickAccessBar(int cellPosition)
	{
		userCharacter.getSpellQuickAccessBarConfig().remove(cellPosition);
	}

    public int getConnectionId()
    {
        return connectionId;
    }

	@SuppressWarnings("unchecked")
	public <T extends Spell> T getKnownSpell(Class<T> type)
	{
		return (T) knownSpells.get(type);
	}

}
