package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.Directions;

public class Player extends GameCharacter
{
    private UserCharacterDataPacket data;
    private float animationFrameDuration = 0.2f;
	private CustomAnimation<TextureRegion> moveDownAnimation;
	private CustomAnimation<TextureRegion> moveLeftAnimation;
	private CustomAnimation<TextureRegion> moveRightAnimation;
	private CustomAnimation<TextureRegion> moveUpAnimation;
	private TextureRegion textureToDraw;

    public Player(long id)
    {
        super(Assets.get("MainChar.png"), id);
        Texture charactersTexture = Assets.get("characters.png");
		TextureRegion[][] charactersTextures = Sprite.split(charactersTexture, charactersTexture.getWidth()/12, charactersTexture.getHeight()/8);
		TextureRegion[][] characterTextures = filter(charactersTextures, 0, 0, 3, 4);
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

    private TextureRegion[][] filter(TextureRegion[][] charactersTextures, int fromX, int fromY, int countX, int countY)
	{
		TextureRegion[][] characterTextures = new TextureRegion[countY][];
		for(int i=0; i<countY; i++)
		{
			characterTextures[i] = new TextureRegion[countX];
			for(int j=0; j<countX; j++)
				characterTextures[i][j] = charactersTextures[i + fromX][j + fromY];
		}
		return characterTextures;
	}

	@Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        int lastMoveDirection = getLastMoveDirection();
        if(lastMoveDirection == Directions.LEFT)
        	useAnimation(moveLeftAnimation, deltaTime);
        else if(lastMoveDirection == Directions.RIGHT)
        	useAnimation(moveRightAnimation, deltaTime);
        else if(lastMoveDirection == Directions.DOWN)
        	useAnimation(moveDownAnimation, deltaTime);
        else if(lastMoveDirection == Directions.UP)
        	useAnimation(moveUpAnimation, deltaTime);
    }
	
	@Override
	public void render(SpriteBatch batch)
	{
		batch.draw(textureToDraw, getX(), getY());
	}
	
	public void useAnimation(CustomAnimation<TextureRegion> animation, float deltaTime)
	{
		animation.update(deltaTime);
		textureToDraw = animation.getKeyFrame();
	}
	

    public void initialize(UserCharacterDataPacket characterData)
    {
        this.data = characterData;
    }
    
    public UserCharacterDataPacket getData()
    {
    	return data;
    }
}
