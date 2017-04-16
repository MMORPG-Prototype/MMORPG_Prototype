package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.objects.CustomAnimation;
import pl.mmorpg.prototype.client.resources.Assets;

public class ExplosionAnimation extends OneLoopAnimationGraphic
{
    public ExplosionAnimation()
    {
        super(createExplosionAnimation());
    }

    public static CustomAnimation<TextureRegion> createExplosionAnimation()
    {
        Texture textureSheet = Assets.get("explosionSheet.png");
        CustomAnimation<TextureRegion> explosionAnimation = new CustomAnimation<>(0.008f,
                Sprite.split(textureSheet, textureSheet.getWidth() / 8, textureSheet.getHeight() / 6));
        return explosionAnimation;
    }

}
