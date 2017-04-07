package pl.mmorpg.prototype.client.objects;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class Player extends GameCharacter
{
    private UserCharacterDataPacket data;

    public Player(long id)
    {
        super(Assets.get("MainChar.png"), id);
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
    }

    public void initialize(UserCharacterDataPacket characterData)
    {
        this.data = characterData;
    }
}
