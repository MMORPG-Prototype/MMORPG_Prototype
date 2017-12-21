package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
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

	public WalkingGameObject(TextureSheetAnimationInfo sheetInfo, long id, CollisionMap<GameObject> linkedCollisionMap)
	{
		super(Assets.get("nullTexture.png"), id, linkedCollisionMap);

		Texture charactersTexture = sheetInfo.texture;
		TextureRegion[][] charactersTextures = Sprite.split(charactersTexture,
				charactersTexture.getWidth() / sheetInfo.textureTileWidth,
				charactersTexture.getHeight() / sheetInfo.textureTileHeight);
		TextureRegion[][] characterTextures = filter(charactersTextures, sheetInfo.textureTileXOffset,
				sheetInfo.textureTileYOffset, sheetInfo.textureCountedTileWidth, sheetInfo.textureCountedTileHeight);
		textureToDraw = characterTextures[0][0];
		setSize(textureToDraw.getRegionWidth(), textureToDraw.getRegionHeight());
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
			CustomAnimation<TextureRegion> moveUpAnimation, CollisionMap<GameObject> linkedCollisionMap)
	{
		super(Assets.get("nullTexture.png"), id, linkedCollisionMap);
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

		MoveInfo moveInfo = getMoveInfo();
		int moveDirection = moveInfo.getMoveDirection();
		float animationDelta = deltaTime*moveInfo.getCurrentMovementSpeed();
		updateAnimation(animationDelta, moveDirection);
	}

	private void updateAnimation(float animationDelta, int moveDirection)
	{
		if (moveDirection == Directions.LEFT)
			useAnimation(moveLeftAnimation, animationDelta);
		else if (moveDirection == Directions.RIGHT)
			useAnimation(moveRightAnimation, animationDelta);
		else if (moveDirection == Directions.DOWN)
			useAnimation(moveDownAnimation, animationDelta);
		else if (moveDirection == Directions.UP)
			useAnimation(moveUpAnimation, animationDelta);
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
