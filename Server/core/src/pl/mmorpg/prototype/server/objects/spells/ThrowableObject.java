package pl.mmorpg.prototype.server.objects.spells;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public abstract class ThrowableObject extends MovableGameObject
{
    private Monster target = null;
    private GameObjectsContainer linkedContainer;
    private PacketsSender packetSender;

    public ThrowableObject(Texture lookout, long id, GameObjectsContainer linkedContainer, PacketsSender packetSender)
    {
        super(lookout, id, packetSender);
        this.linkedContainer = linkedContainer;
        this.packetSender = packetSender;
    }

    @Override
    public void update(float deltaTime)
    {
        if (hasTarget() && targedIsAlive())
        {
            chaseTarget(deltaTime, target);
            if (isNearTarget(target))
            {
                removeItself(linkedContainer, packetSender);
                onFinish(target);
            }
        }
        else
            removeItself(linkedContainer, packetSender);
        super.update(deltaTime);
    }

    private boolean targedIsAlive()
    {
        return target.isAlive();
    }

    public abstract void onFinish(Monster target);

    private boolean hasTarget()
    {
        return target != null;
    }

    public void setTarget(Monster target)
    {
        this.target = target;
    }

}
