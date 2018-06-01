package pl.mmorpg.prototype.client.objects.graphic;

import java.util.function.Supplier;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.resources.Assets;

public class GameObjectNameGraphic extends GraphicGameObject
{
	private final GameObject gameObject;
	private final Supplier<Boolean> removalCondition;
	private final BitmapFont font = Assets.getFont();

	public GameObjectNameGraphic(GameObject gameObject, Supplier<Boolean> removalCondition)
	{
		this.gameObject = gameObject;
		this.removalCondition = removalCondition;
	}
	
	@Override
	public void update(float deltaTime)
	{
		x = gameObject.getX();
		y = gameObject.getY() + gameObject.getHeight() + 10;
	}

	@Override
	public void render(SpriteBatch batch)
	{
		font.draw(batch, gameObject.getName(), x, y);
	}

	@Override
	public boolean shouldDelete()
	{
		return removalCondition.get();
	}

}
