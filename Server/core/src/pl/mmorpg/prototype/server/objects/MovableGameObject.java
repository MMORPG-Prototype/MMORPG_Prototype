package pl.mmorpg.prototype.server.objects;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public abstract class MovableGameObject extends GameObject
{
    private static final float stopChasingDistance = 5.0f;
    private float moveSpeed = 150.0f;
    private float movementX = 0.0f;
    private float movementY = 0.0f;
    private float packetSendingInterval = 0.20f;
    private float currentPacketSendingTime = 0.0f;
    private PacketsSender packetsSender;

    protected MovableGameObject(Texture lookout, long id, PacketsSender packetsSender)
    {
        super(lookout, id);
        this.packetsSender = packetsSender;
    }

    @Override
    public void update(float deltaTime)
    {
        currentPacketSendingTime += deltaTime;
    }

    public GameObject moveRight(CollisionMap<GameObject> collisionMap, float deltaTime)
    {
        movementX += getMoveSpeed() * deltaTime;
        int collisionMoveValue = (int) movementX;
        movementX -= (int) movementX;
        GameObject collision = moveRight(collisionMap, collisionMoveValue);
        if (collision != null)
        {
            while (moveRight(collisionMap, 1) == null)
                ;
        }
        if (collision == null)
            sendRepositionPacket();
        return collision;
    }

    protected void sendRepositionPacket()
    {
        if (currentPacketSendingTime >= packetSendingInterval)
        {
            currentPacketSendingTime = 0.0f;
            packetsSender.sendToAll(PacketsMaker.makeRepositionPacket(this));
        }
    }

    public GameObject moveLeft(CollisionMap<GameObject> collisionMap, float deltaTime)
    {
        movementX -= getMoveSpeed() * deltaTime;
        int collisionMoveValue = (int) -movementX;
        movementX -= (int) movementX;
        GameObject collision = moveLeft(collisionMap, collisionMoveValue);
        if (collision != null)
        {
            while (moveLeft(collisionMap, 1) == null)
                ;
        }

        if (collision == null)
            sendRepositionPacket();
        return collision;
    }

    public GameObject moveDown(CollisionMap<GameObject> collisionMap, float deltaTime)
    {
        movementY -= getMoveSpeed() * deltaTime;
        int collisionMoveValue = (int) -movementY;
        movementY -= (int) movementY;
        GameObject collision = moveDown(collisionMap, collisionMoveValue);
        if (collision != null)
        {
            while (moveDown(collisionMap, 1) == null)
                ;
        }

        if (collision == null)
            sendRepositionPacket();
        return collision;
    }

    public GameObject moveUp(CollisionMap<GameObject> collisionMap, float deltaTime)
    {
        movementY += getMoveSpeed() * deltaTime;
        int collisionMoveValue = (int) movementY;
        movementY -= (int) movementY;
        GameObject collision = moveUp(collisionMap, collisionMoveValue);
        if (collision != null)
        {
            while (moveUp(collisionMap, 1) == null)
                ;
        }

        if (collision == null)
            sendRepositionPacket();
        return collision;
    }

    public GameObject moveRight(CollisionMap<GameObject> collisionMap, int moveValue)
    {
        GameObject collision = collisionMap.tryToRepositionCollisionGoingRight(moveValue, this);
        if (collision == null)
            setX(getX() + moveValue * collisionMap.getScale());
        return collision;
    }

    public GameObject moveLeft(CollisionMap<GameObject> collisionMap, int moveValue)
    {
        GameObject collision = collisionMap.tryToRepositionCollisionGoingLeft(moveValue, this);
        if (collision == null)
            setX(getX() - moveValue * collisionMap.getScale());
        return collision;
    }

    public GameObject moveUp(CollisionMap<GameObject> collisionMap, int moveValue)
    {
        GameObject collision = collisionMap.tryToRepositionCollisionGoingUp(moveValue, this);
        if (collision == null)
            setY(getY() + moveValue * collisionMap.getScale());
        return collision;
    }

    public GameObject moveDown(CollisionMap<GameObject> collisionMap, int moveValue)
    {
        GameObject collision = collisionMap.tryToRepositionCollisionGoingDown(moveValue, this);
        if (collision == null)
            setY(getY() - moveValue * collisionMap.getScale());
        return collision;
    }

    public void moveLeft(float deltaTime)
    {
        setX(getX() - getMoveSpeed() * deltaTime);
        sendRepositionPacket();
    }

    public void moveRight(float deltaTime)
    {
        setX(getX() + getMoveSpeed() * deltaTime);
        sendRepositionPacket();
    }

    public void moveDown(float deltaTime)
    {
        setY(getY() - getMoveSpeed() * deltaTime);
        sendRepositionPacket();
    }

    public void moveUp(float deltaTime)
    {
        setY(getY() + getMoveSpeed() * deltaTime);
        sendRepositionPacket();
    }

    public float getMoveSpeed()
    {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed)
    {
        this.moveSpeed = moveSpeed;
    }

    protected void setPacketSendingInterval(float packetSendingInterval)
    {
        this.packetSendingInterval = packetSendingInterval;
    }
    
    protected void chaseTarget(float deltaTime, CollisionMap<GameObject> collisionMap, Monster target)
    {
        float deltaX = target.getX() - getX();
        if(Math.abs(deltaX) > stopChasingDistance)
            if (deltaX > 0)
                moveRight(collisionMap, deltaTime);
            else
                moveLeft(collisionMap, deltaTime);

        float deltaY = target.getY() - getY();
        if(Math.abs(deltaY) > stopChasingDistance)
            if (deltaY > 0)
                moveUp(collisionMap, deltaTime);
            else
                moveDown(collisionMap, deltaTime);

    }
    
    protected void chaseTarget(float deltaTime, Monster target)
    {
        float deltaX = target.getX() - getX();
        if(Math.abs(deltaX) > stopChasingDistance)
            if (deltaX > 0)
                moveRight(deltaTime);
            else
                moveLeft(deltaTime);

        float deltaY = target.getY() - getY();
        if(Math.abs(deltaY) > stopChasingDistance)
            if (deltaY > 0)
                moveUp(deltaTime);
            else
                moveDown(deltaTime);
    }
    
    protected boolean isNearTarget(Monster target)
    {
        return Math.abs(target.getX() - getX()) < stopChasingDistance &&
                Math.abs(target.getY() - getY()) < stopChasingDistance;
    }

}
