package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.monsters.HealthBarMonster;
import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo;
import pl.mmorpg.prototype.client.packethandlers.GameObjectTargetPacketHandler;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.GoldAmountChangePacket;
import pl.mmorpg.prototype.clientservercommon.packets.GoldReceivePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.PlayerPropertiesBuilder;

public class Player extends HealthBarMonster
{
    private UserCharacterDataPacket data;
    private Texture lockOnTexture = Assets.get("target.png");

    public Player(long id, CollisionMap<GameObject> collisionMap,
			UserCharacterDataPacket characterData, PacketHandlerRegisterer registerer)
    {
    	this(id, collisionMap, characterData, new PlayerPropertiesBuilder(characterData).build(), registerer);
    }

	public Player(long id, CollisionMap<GameObject> collisionMap,
			UserCharacterDataPacket characterData, MonsterProperties properties, PacketHandlerRegisterer registerer)
	{
		super(new TextureSheetAnimationInfo
						.Builder(Assets.get("characters.png"))
						.textureTileWidth(12)
						.textureTileHeight(8)
						.textureCountedTileWidth(3)
						.textureCountedTileHeight(4)
						.textureTileXOffset(0)
						.textureTileYOffset(0)
						.build(),
				id, properties, collisionMap, registerer);
		disableSliding();
		registerPacketHandler(new GoldReceivePacketHandler());
		registerPacketHandler(new GoldAmountChangePacketHandler());

		this.data = characterData;
		this.setName(characterData.getNickname());
		recreateHealthBar();
		initPosition(data.getStartingX(), data.getStartingY());
	}

    @Override
    public void render(SpriteBatch batch)
    {
        super.render(batch);
        if (hasLockedOnTarget())
        {
            GameObject target = getTarget();
            batch.draw(lockOnTexture, target.getX(), target.getY(), target.getWidth(), target.getHeight());
        }
    }

    public UserCharacterDataPacket getData()
    {
        return data;
    }

    public void addExperience(int experienceGain)
    {
    	getProperties().experience += experienceGain;
        data.setExperience(data.getExperience() + experienceGain);
    }

	public void addLevel(int levelUpPoints)
	{
		getProperties().level += 1;
		data.setLevel(data.getLevel() + 1);
		data.setLevelUpPoints(data.getLevelUpPoints() + levelUpPoints);
	}

    @Override
    public void gotHitBy(int damage)
    {
        data.setHitPoints(data.getHitPoints() - damage);
        super.gotHitBy(damage);
    }
    
    public void manaDrained(int manaDrain)
    {
        Integer manaPoints = data.getManaPoints();
        manaPoints -= manaDrain;
        if(manaPoints < 0)
            manaPoints = 0;
        data.setManaPoints(manaPoints);
        getProperties().mp = manaPoints;
    }

	public void addStrength()
	{
		getProperties().strength += 1;
		data.setStrength(data.getStrength() + 1);
	}

	public void addIntelligence()
	{
		getProperties().intelligence += 1;
		data.setIntelligence(data.getIntelligence() + 1);
	}

	public void addDexterity()
	{
		getProperties().strength += 1;
		data.setDexterity(data.getDexterity() + 1);
	}

	public void decreaseLevelUpPoints()
	{
		data.setLevelUpPoints(data.getLevelUpPoints() - 1);
	}

	private class GoldReceivePacketHandler extends PacketHandlerBase<GoldReceivePacket>
    	implements GameObjectTargetPacketHandler<GoldReceivePacket>
    {
		@Override
		protected void doHandle(GoldReceivePacket packet)
		{
			System.out.println("Player gold packet handling");
			getProperties().gold += packet.getGoldAmount();
		}

		@Override
		public long getObjectId()
		{
			return getId();
		}
    }
    
    private class GoldAmountChangePacketHandler extends PacketHandlerBase<GoldAmountChangePacket>
		implements GameObjectTargetPacketHandler<GoldAmountChangePacket>
    {
		@Override
		protected void doHandle(GoldAmountChangePacket packet)
		{
			getProperties().gold = packet.getNewGoldAmount();
		}
		
		@Override
		public long getObjectId()
		{
			return getId();
		}
    }
    
    @Override
    public void unregisterHandlers(PacketHandlerRegisterer registerer)
    {
    	super.unregisterHandlers(registerer);
    }

}
