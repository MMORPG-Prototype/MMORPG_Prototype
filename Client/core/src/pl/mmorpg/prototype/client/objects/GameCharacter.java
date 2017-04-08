package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;

public abstract class GameCharacter extends MovableGameObject
{
    public GameCharacter(Texture lookout, long id)
    {
        super(lookout, id);
    }

}
