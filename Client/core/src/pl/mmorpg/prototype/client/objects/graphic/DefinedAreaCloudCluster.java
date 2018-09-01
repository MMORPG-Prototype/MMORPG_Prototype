package pl.mmorpg.prototype.client.objects.graphic;

import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DefinedAreaCloudCluster extends GraphicGameObject
{
	private final float width;
	private final float height;
	private Collection<Clouds> cloudCluster = new ArrayList<>();

	public DefinedAreaCloudCluster(float width, float height)
	{
		this.width = width;
		this.height = height;
		initializeCloudCluster(width, height);
	}

	private void initializeCloudCluster(float width, float height)
	{
		int cloudTextureWidth = Clouds.getTexture().getWidth();
		int cloudTextureHeight = Clouds.getTexture().getHeight();
		int numberOfCloudsInWidth = (int) Math.ceil(width / cloudTextureWidth);
		int numberOfCloudsInHeight = (int) Math.ceil(height / cloudTextureHeight);
		for (int i = 0; i < numberOfCloudsInWidth; i++)
			for (int j = 0; j < numberOfCloudsInHeight; j++)
				cloudCluster.add(new Clouds(i * cloudTextureWidth, j * cloudTextureHeight));
	}

	@Override
	public void update(float deltaTime)
	{
		cloudCluster.forEach(clouds -> clouds.update(deltaTime));
	}

	@Override
	public void render(SpriteBatch batch)
	{
		cloudCluster.forEach(clouds -> clouds.render(batch));
	}

	@Override
	public boolean shouldDelete()
	{
		return false;
	}
}
