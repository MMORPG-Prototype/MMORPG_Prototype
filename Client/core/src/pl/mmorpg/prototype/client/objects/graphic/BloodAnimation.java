package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.objects.CustomAnimation;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.resources.Assets;

public class BloodAnimation extends OneLoopAnimationGraphic
{
	
	public BloodAnimation(GameObject source)
	{
		super(createBloodAnimation());
		x = source.getX() - 6;
		y = source.getY() - 12;
		width = 44;
		height = 44;
	}
	
	private static CustomAnimation<TextureRegion> createBloodAnimation()
	{
		Texture bloodSheet = Assets.get("bloodSheetAnimation.png");
		TextureRegion[][] textures = TextureRegion.split(bloodSheet, bloodSheet.getWidth()/4, bloodSheet.getHeight()/4);
		CustomAnimation<TextureRegion> animation = new CustomAnimation<>(0.1f, textures);
		return animation;
	}

}
