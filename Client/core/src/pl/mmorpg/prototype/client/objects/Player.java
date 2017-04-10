package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class Player extends AttackingGameObject
{
    private UserCharacterDataPacket data;
    private Texture lockOnTexture = Assets.get("target.png");

    public Player(long id)
    {
        super(Assets.get("characters.png"), 0, 0, id);
        
    }

    public void initialize(UserCharacterDataPacket characterData)
    {
        this.data = characterData;
    }
    
    @Override
    public void render(SpriteBatch batch)
    {
    	super.render(batch);
    	if(hasLockedOnTarget())
    	{
    		GameObject target = getTarget();
    		batch.draw(lockOnTexture, target.getX(), target.getY(), target.getWidth(), target.getHeight());
    	}
    }
    
    public UserCharacterDataPacket getData()
    {
    	return data;
    }


}
