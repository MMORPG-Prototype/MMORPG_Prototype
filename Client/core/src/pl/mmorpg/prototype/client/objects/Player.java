package pl.mmorpg.prototype.client.objects;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class Player extends WalkingGameObject
{
    private UserCharacterDataPacket data;

    public Player(long id)
    {
        super(Assets.get("characters.png"), 0, 0, id);
        
    }

    public void initialize(UserCharacterDataPacket characterData)
    {
        this.data = characterData;
    }
    
    public UserCharacterDataPacket getData()
    {
    	return data;
    }
}
