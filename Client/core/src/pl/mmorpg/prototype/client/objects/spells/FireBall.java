package pl.mmorpg.prototype.client.objects.spells;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.objects.CustomAnimation;
import pl.mmorpg.prototype.client.objects.GraphicObjectsContainer;
import pl.mmorpg.prototype.client.objects.graphic.ExplosionAnimation;
import pl.mmorpg.prototype.client.resources.Assets;

public class FireBall extends ThrowableObject
{
    private CustomAnimation<TextureRegion> fireBallAnimation;
    
    public FireBall(long id)
    {
        super(Assets.get("32x32.bmp"), id);
        Texture textureSheet = Assets.get("fireballSheet.png");
        fireBallAnimation = new CustomAnimation<>(0.16f,
                TextureRegion.split(textureSheet, textureSheet.getWidth() / 3, textureSheet.getHeight()));
        fireBallAnimation.setPlayMode(PlayMode.LOOP);
        setRegion(fireBallAnimation.getKeyFrame());
        setStepSpeed(170.0f);
    }
    
    @Override
    public void update(float deltaTime)
    {
        fireBallAnimation.update(deltaTime);
        setRegion(fireBallAnimation.getKeyFrame());
        super.update(deltaTime);
    }
    
    @Override
    public void onRemoval(GraphicObjectsContainer graphics)
    {
        ExplosionAnimation explosion = new ExplosionAnimation();
        explosion.setX(getX());
        explosion.setY(getY());
        graphics.add(explosion);
        super.onRemoval(graphics);
    }

}
