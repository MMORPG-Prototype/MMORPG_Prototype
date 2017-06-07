package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.movement.Directions;

public abstract class WalkingGameObject extends MovableGameObject
{
	private final CustomAnimation<TextureRegion> moveDownAnimation;
	private final CustomAnimation<TextureRegion> moveLeftAnimation;
	private final CustomAnimation<TextureRegion> moveRightAnimation;
	private final CustomAnimation<TextureRegion> moveUpAnimation;
	private TextureRegion textureToDraw;
	private float animationFrameDuration = 0.16f;

	public WalkingGameObject(TextureSheetAnimationInfo sheetInfo, long id)
	{
		super(Assets.get("nullTexture.png"), id);

		Texture charactersTexture = sheetInfo.texture;
		TextureRegion[][] charactersTextures = Sprite.split(charactersTexture,
				charactersTexture.getWidth() / sheetInfo.textureTileWidth,
				charactersTexture.getHeight() / sheetInfo.textureTileHeight);
		TextureRegion[][] characterTextures = filter(charactersTextures, sheetInfo.textureTileXOffset,
				sheetInfo.textureTileYOffset, sheetInfo.textureCountedTileWidth, sheetInfo.textureCountedTileHeight);
		textureToDraw = characterTextures[0][0];
		moveDownAnimation = new CustomAnimation<>(animationFrameDuration, characterTextures[0]);
		moveLeftAnimation = new CustomAnimation<>(animationFrameDuration, characterTextures[1]);
		moveRightAnimation = new CustomAnimation<>(animationFrameDuration, characterTextures[2]);
		moveUpAnimation = new CustomAnimation<>(animationFrameDuration, characterTextures[3]);
		moveUpAnimation.setPlayMode(PlayMode.LOOP);
		moveLeftAnimation.setPlayMode(PlayMode.LOOP);
		moveDownAnimation.setPlayMode(PlayMode.LOOP);
		moveRightAnimation.setPlayMode(PlayMode.LOOP);
	}

	public WalkingGameObject(long id, CustomAnimation<TextureRegion> moveLeftAnimation,
			CustomAnimation<TextureRegion> moveRightAnimation, CustomAnimation<TextureRegion> moveDownAnimation,
			CustomAnimation<TextureRegion> moveUpAnimation)
	{
		super(Assets.get("nullTexture.png"), id);
		this.moveLeftAnimation = moveLeftAnimation;
		this.moveRightAnimation = moveRightAnimation;
		this.moveDownAnimation = moveDownAnimation;
		this.moveUpAnimation = moveUpAnimation;
	}

	private TextureRegion[][] filter(TextureRegion[][] charactersTextures, int fromX, int fromY, int countX, int countY)
	{
		TextureRegion[][] characterTextures = new TextureRegion[countY][];
		for (int i = 0; i < countY; i++)
		{
			characterTextures[i] = new TextureRegion[countX];
			for (int j = 0; j < countX; j++)
				characterTextures[i][j] = charactersTextures[i + fromY][j + fromX];
		}
		return characterTextures;
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);

		int lastMoveDirection = getLastMoveDirection();
		if (lastMoveDirection == Directions.LEFT)
			useAnimation(moveLeftAnimation, deltaTime);
		else if (lastMoveDirection == Directions.RIGHT)
			useAnimation(moveRightAnimation, deltaTime);
		else if (lastMoveDirection == Directions.DOWN)
			useAnimation(moveDownAnimation, deltaTime);
		else if (lastMoveDirection == Directions.UP)
			useAnimation(moveUpAnimation, deltaTime);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		batch.draw(textureToDraw, getX(), getY());
	}

	@Override
	public float getWidth()
	{
		return textureToDraw.getRegionWidth();
	}

	@Override
	public float getHeight()
	{
		return textureToDraw.getRegionHeight();
	}

	public void useAnimation(CustomAnimation<TextureRegion> animation, float deltaTime)
	{
		animation.update(deltaTime);
		textureToDraw = animation.getKeyFrame();
	}
}
