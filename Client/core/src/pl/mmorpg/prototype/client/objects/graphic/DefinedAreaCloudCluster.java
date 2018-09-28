package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

public class DefinedAreaCloudCluster extends GraphicGameObject
{
	private final float width;
	private final float height;
	private final LinkedList<LinkedList<Clouds>> cloudCluster;
	private final int cloudTextureWidth;
	private final int cloudTextureHeight;

	public DefinedAreaCloudCluster(float width, float height)
	{
		this.width = width;
		this.height = height;
		cloudTextureWidth = Clouds.getTexture().getWidth();
		cloudTextureHeight = Clouds.getTexture().getHeight();
		int numberOfCloudsInWidth = (int) Math.ceil(width / cloudTextureWidth);
		int numberOfCloudsInHeight = (int) Math.ceil(height / cloudTextureHeight);
		cloudCluster = createCloudRectangleCluster(numberOfCloudsInWidth, numberOfCloudsInHeight);
	}

	private LinkedList<LinkedList<Clouds>> createCloudRectangleCluster(int numberOfCloudsInWidth, int numberOfCloudsInHeight)
	{
		LinkedList<LinkedList<Clouds>> cloudCluster = new LinkedList<>();
		for (int i = 0; i < numberOfCloudsInWidth; i++)
		{
			LinkedList<Clouds> cloudsLine = new LinkedList<>();
			for (int j = 0; j < numberOfCloudsInHeight; j++)
				cloudsLine.add(new Clouds(i * cloudTextureWidth, j * cloudTextureHeight));
			cloudCluster.add(cloudsLine);
		}
		return cloudCluster;
	}

	@Override
	public void update(float deltaTime)
	{
		cloudCluster.forEach(cloudsLine -> cloudsLine.forEach(clouds -> clouds.update(deltaTime)));
		Clouds bottomLeftCornerCloudsChunk = getBottomLeftCornerCloudsChunk();
		if (bottomLeftCornerCloudsChunk.x > this.x)
			addLineOfCloudsToTheLeft();
		if (bottomLeftCornerCloudsChunk.y + cloudTextureHeight < this.y)
			removeLineOfCloudsFromBottom();
		Clouds upperRightCornerCloudsChunk = getUpperRightCornerCloudsChunk();
		if (upperRightCornerCloudsChunk.y + cloudTextureHeight < this.y + this.height)
			addLineOfCloudsToTheTop();
		if (upperRightCornerCloudsChunk.x > this.x + this.width)
			removeLineOfCloudsFromRight();
	}

	private void addLineOfCloudsToTheLeft()
	{
		Clouds upperLeftCornerCloudsChunk = getBottomLeftCornerCloudsChunk();
		LinkedList<Clouds> cloudsLine = new LinkedList<>();
		int numberOfCloudsInHeight = cloudCluster.get(0).size();
		for (int j = 0; j < numberOfCloudsInHeight; j++)
			cloudsLine.add(new Clouds(upperLeftCornerCloudsChunk.x - cloudTextureWidth, j * cloudTextureHeight));
		cloudCluster.addFirst(cloudsLine);
	}

	private void removeLineOfCloudsFromBottom()
	{
		cloudCluster.forEach(LinkedList::removeFirst);
	}

	private void addLineOfCloudsToTheTop()
	{
		cloudCluster.forEach(cloudLine -> cloudLine.addLast(new Clouds(cloudLine.getLast().x, cloudLine.getLast().y + cloudTextureHeight)));
	}

	private void removeLineOfCloudsFromRight()
	{
		cloudCluster.removeLast();
	}

	private Clouds getBottomLeftCornerCloudsChunk()
	{
		return cloudCluster.get(0).get(0);
	}

	private Clouds getUpperRightCornerCloudsChunk()
	{
		return cloudCluster.getLast().getLast();
	}

	@Override
	public void render(SpriteBatch batch)
	{
		cloudCluster.forEach(cloudsLine -> cloudsLine.forEach(clouds -> clouds.render(batch)));
	}

	@Override
	public void setX(float x)
	{
		//TODO move all cluster
		super.setX(x);
	}

	@Override
	public void setY(float y)
	{
		//TODO move all cluster
		super.setY(y);
	}

	@Override
	public boolean shouldDelete()
	{
		return false;
	}
}
