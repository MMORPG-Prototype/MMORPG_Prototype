package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.resources.Assets;

public class FireBall extends MovableGameObject
{
    private CustomAnimation<TextureRegion> fireBallAnimation;

    public FireBall(long id)
    {
        super(Assets.get("fireballSheet.png"), id);
        Texture textureSheet = Assets.get("fireballSheet.png");
        fireBallAnimation = new CustomAnimation<>(0.16f,
                TextureRegion.split(textureSheet, textureSheet.getWidth() / 3, textureSheet.getHeight()));
        fireBallAnimation.setPlayMode(PlayMode.LOOP);
    }

}
