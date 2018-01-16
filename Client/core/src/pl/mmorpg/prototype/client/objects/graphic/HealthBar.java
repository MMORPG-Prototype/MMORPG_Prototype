package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.resources.Assets;

public class HealthBar extends GraphicGameObject
{
	private boolean shouldDelete = false;
	private int hp;
	private final int maxHp;
	
	private Texture redRectangle = Assets.get("redRectangle.png");
	private Texture blackEmptyRectangle = Assets.get("blackEmptyRectangle.png");
	private GameObject owner;
	
	public HealthBar(int maxHp, GameObject owner)
	{
		this.owner = owner;
		this.maxHp = maxHp;
		hp = maxHp;
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		float width = (float)hp/maxHp * blackEmptyRectangle.getWidth();
		batch.draw(redRectangle, x, y, width, blackEmptyRectangle.getHeight());
		batch.draw(blackEmptyRectangle, x, y);
	}
	
	public void updateBar(int hp)
	{
		this.hp = hp;
	}

	@Override
	public boolean shouldDelete()
	{
		return shouldDelete;
	}
	
	public void delete()
	{
		shouldDelete = true;
	}

	@Override
	public void update(float deltaTime)
	{
		x = owner.getX();
		y = owner.getY() + 40;
	}

}
