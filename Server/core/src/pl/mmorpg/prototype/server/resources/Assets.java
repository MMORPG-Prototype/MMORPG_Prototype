package pl.mmorpg.prototype.server.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.mockito.Mockito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import pl.mmorpg.prototype.server.exceptions.GameException;

public class Assets
{
	private static String assetsPath = "assets";
	private static Map<String, Class<?>> classTypes = new HashMap<String, Class<?>>();
	private static AssetManager assets = new AssetManager();
	private static BitmapFont font = new BitmapFont();
	private static SpriteBatch batch = Mockito.mock(SpriteBatch.class);

	public static void loadAssets()
	{
		assets.setLoader(TiledMap.class, new TmxMapLoader());
		addClassTypes();
        loadAll();
	}

	private static void addClassTypes()
    {
        classTypes.put("png", Texture.class);
        classTypes.put("jpg", Texture.class);
        classTypes.put("bmp", Texture.class);
        classTypes.put("gif", Texture.class);
        classTypes.put("mp3", Music.class);
        classTypes.put("ogg", Sound.class);
        classTypes.put("tmx", TiledMap.class);
    }

	private static void loadAll()
    {
		Collection<FileHandle> fileHandles = new ArrayList<>();
        fileHandles = loadFromSubdirectories(assetsPath, fileHandles);
		for (FileHandle file : fileHandles)
			assets.load(file.path(), getClassFromPath(file.path()));

        assets.finishLoading();
    }
	
	private static Collection<FileHandle> loadFromSubdirectories(String path, Collection<FileHandle> fileHandles)
	{
		FileHandle[] files = Gdx.files.internal(path).list();
		for (FileHandle file : files)
		{
			if(file.isDirectory())
				fileHandles = loadFromSubdirectories(file.path(), fileHandles);
			else
				fileHandles = tryAddingFile(file, fileHandles);
		}
		return fileHandles;
	}

	private static Collection<FileHandle> tryAddingFile(FileHandle file, Collection<FileHandle> fileHandles)
	{

			fileHandles.add(file);
			return fileHandles;
	}

	private static Class<?> getClassFromPath(String path)
    {
        String extension = getExtension(path);
        if (classTypes.containsKey(extension))
            return classTypes.get(extension);
        else
            throw new UnknownExtensionException(extension);
    }

	private static String getExtension(String path)
    {
        int extensionStartIndex = path.lastIndexOf('.') + 1;
        return path.substring(extensionStartIndex);
    }

	public static <T> T get(String fileName)
    {
        T asset = assets.get(assetsPath + '/' + fileName);
        return asset;
    }

	public static <T> T get(String fileName, Class<T> classType)
    {
        return assets.get(assetsPath + '/' + fileName, classType);
    }

	public static void dispose()
    {
        assets.dispose();
        font.dispose();
    }

	public static BitmapFont getFont()
    {
        return font;
    }

	private static class UnknownExtensionException extends GameException
    {
        public UnknownExtensionException(String extension)
        {
            super("Extension " + extension + " is not recognized");
        }
    }

	public static SpriteBatch getBatch()
	{
		return batch;
	}

}
