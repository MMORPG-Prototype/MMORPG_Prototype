package pl.mmorpg.prototype.client.objects.monsters;

import com.badlogic.gdx.graphics.Texture;

public class TextureSheetAnimationInfo
{
	private TextureSheetAnimationInfo(){}
	
	public Texture texture;
	public int textureTileWidth;
	public int textureTileHeight;
	public int textureTileXOffset;
	public int textureTileYOffset;
	
	public static class Builder
	{
		TextureSheetAnimationInfo sheetInfo = new TextureSheetAnimationInfo();
		
		
	}
}
