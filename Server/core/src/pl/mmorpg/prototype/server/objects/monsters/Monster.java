package pl.mmorpg.prototype.server.objects.monsters;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class Monster extends MovableGameObject
{
    private static final BitmapFont font = Assets.getFont();
    protected final MonsterProperties properties;
    private Monster targetedMonster = null;
    private float hitTime = 1000.0f;
    protected PlayState linkedState;

    private List<Monster> targetedBy = new LinkedList<>();

    public Monster(Texture lookout, long id, PlayState playState, MonsterProperties properties)
    {
        super(lookout, id, playState);
        linkedState = playState;
        font.setColor(new Color(1, 0, 0, 1));
        this.properties = properties;
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        if (isTargetingAnotherMonster())
            attackHandle(deltaTime);

    }

    public boolean isTargetingAnotherMonster()
    {
        return targetedMonster != null;
    }

    private void attackHandle(float deltaTime)
    {
        hitTime += deltaTime;
        if (canAttackTarget())
        {
            hitTime = 0.0f;
            normalAttack(targetedMonster);
        }
    }

    private boolean canAttackTarget()
    {
        return hitTime >= properties.attackSpeed && distance(targetedMonster) <= properties.attackRange;
    }

    private double distance(Monster targetedMonster)
    {
        float selfCenterX = getX() + getWidth() / 2;
        float targetCenterX = targetedMonster.getX() + targetedMonster.getWidth() / 2;
        float deltaX = targetCenterX - selfCenterX;
        float selfCenterY = getY() + getHeight() / 2;
        float targetCenterY = targetedMonster.getY() + targetedMonster.getHeight() / 2;
        float deltaY = targetCenterY - selfCenterY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance;
    }

    private void normalAttack(Monster target)
    {
        int damage = DamageCalculator.getDamage(this, target);
        target.properties.hp -= damage;
        linkedState.send(PacketsMaker.makeNormalDamagePacket(target.getId(), damage));
        if (target.properties.hp <= 0)
        {
            this.killed(target);
            target.die();
        }
    }

    @Override
    public void render(SpriteBatch batch)
    {
        super.render(batch);
        font.draw(batch, String.valueOf(properties.hp), getX() + 3, getY() + 40);
    }

    public void targetMonster(Monster target)
    {
        targetedMonster = target;
        target.isTargetedBy(this);
    }

    public Monster getTargetedMonster()
    {
        return targetedMonster;
    }

    private void isTargetedBy(Monster source)
    {
        targetedBy.add(source);
    }

    public void die()
    {
        linkedState.remove(getId());
        linkedState.send(PacketsMaker.makeRemovalPacket(getId()));
        for (Monster targetedBY : targetedBy)
            targetedBY.targetedMonster = null;
    }

    public void killed(Monster target)
    {
        targetedMonster = null;
    }

    public MonsterProperties getProperties()
    {
        return properties;
    }
    
    public boolean isInGame()
    {
    	return linkedState.has(getId());
    }
    
    protected void stopTargetingMonster()
    {
    	targetedMonster = null;
    }
    
    @Override
    public void onRemoval()
    {
    	for (Monster targetedBY : targetedBy)
            targetedBY.targetedMonster = null;
    	super.onRemoval();
    }

}