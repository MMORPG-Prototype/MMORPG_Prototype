package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.objects.monsters.HealthBarMonster;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.PlayerPropertiesBuilder;

public class Player extends HealthBarMonster
{
    private UserCharacterDataPacket data;
    private Texture lockOnTexture = Assets.get("target.png");

    public Player(long id)
    {
        super(Assets.get("characters.png"), 0, 0, id, new MonsterProperties.Builder().build());
    }

    public void initialize(UserCharacterDataPacket characterData)
    {
        this.data = characterData;
        setProperties(new PlayerPropertiesBuilder(characterData).build());
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
        data.setExperience(data.getExperience() + experienceGain);
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

}
