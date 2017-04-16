package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.objects.CustomAnimation;

public class InfiniteAnimationGraphic extends GraphicGameObject
{
    protected CustomAnimation<TextureRegion> animation;
    protected float width;
    protected float height;

    public InfiniteAnimationGraphic(CustomAnimation<TextureRegion> animation)
    {
        this.animation = animation;
        animation.setPlayMode(PlayMode.LOOP);
        width = animation.getKeyFrame().getRegionWidth();
        height = animation.getKeyFrame().getRegionHeight();
    }

    @Override
    public void update(float deltaTime)
    {
        animation.update(deltaTime);
        super.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(animation.getKeyFrame(), x, y, width, height);
    }

    @Override
    public boolean shouldDelete()
    {
        return false;
    }

}
