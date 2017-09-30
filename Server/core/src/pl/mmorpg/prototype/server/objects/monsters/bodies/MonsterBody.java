package pl.mmorpg.prototype.server.objects.monsters.bodies;

import java.awt.Point;
import java.util.Collection;

import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.server.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionObject;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.containers.GameContainer;
import pl.mmorpg.prototype.server.objects.items.Item;

public class MonsterBody extends GameObject implements StackableCollisionObject
{
    private GameContainer loot;
    private int collisionContainerId = -1;

    public MonsterBody(Texture lookout, long id, int gold, Collection<Item> loot)
    {
        super(lookout, id);
        this.loot = new GameContainer(id, loot, gold);
    }

    @Override
    public void update(float deltaTime)
    {
    }

    public GameContainer getContainer()
    {
        return loot;
    }

    @Override
    public Point getCenter()
    {
        return new Point((int) (getX() + getWidth() / 2), (int) (getY() + getHeight() / 2));
    }

    @Override
    public boolean isColliding(RectangleCollisionObject object)
    {
        return getCollisionRect().overlaps(object.getCollisionRect());
    }

    @Override
    public int getCollisionContainerId()
    {
        if (collisionContainerId == -1)
            Log.warn("Container id was not set, but it is used. "
                    + "If this message shows more than once per container, "
                    + "there is a bug in your code");
        return collisionContainerId;
    }

    @Override
    public void setCollisionContainerId(int id)
    {
        collisionContainerId = id;
    }

}
