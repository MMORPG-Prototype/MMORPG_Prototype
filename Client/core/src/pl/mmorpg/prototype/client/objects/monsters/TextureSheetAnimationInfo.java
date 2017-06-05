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
	public int textureCountedTileWidth;
	public int textureCountedTileHeight;
	
	public static class Builder
	{
		private TextureSheetAnimationInfo sheetInfo = new TextureSheetAnimationInfo();
		
		public Builder(Texture texture)
		{
			sheetInfo.texture = texture;
		}
		
		public Builder textureTileWidth(int textureTileWidth)
		{
			sheetInfo.textureTileWidth = textureTileWidth;
			return this;
		}
		
		public Builder textureTileHeight(int textureTileHeight)
		{
			sheetInfo.textureTileHeight = textureTileHeight;
			return this;
		}
		
		public Builder textureTileXOffset(int textureTileXOffset)
		{
			sheetInfo.textureTileXOffset = textureTileXOffset;
			return this;
		}
		
		public Builder textureTileYOffset(int textureTileYOffset)
		{
			sheetInfo.textureTileYOffset = textureTileYOffset;
			return this;
		}
		
		public Builder textureCountedTileWidth(int textureCountedTileWidth)
		{
			sheetInfo.textureCountedTileWidth = textureCountedTileWidth;
			return this;
		}
		
		public Builder textureCountedTileHeight(int textureCounterTileHeight)
		{
			sheetInfo.textureCountedTileHeight = textureCounterTileHeight;
			return this;
		}
		
		public TextureSheetAnimationInfo build()
		{
			return sheetInfo;
		}
	}
}
