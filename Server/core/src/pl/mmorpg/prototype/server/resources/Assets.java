package pl.mmorpg.prototype.server.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	private static Map<String, Class<?>> classTypes = new HashMap();
	private static AssetManager assets = new AssetManager();
	private static BitmapFont font = new BitmapFont();
	private static SpriteBatch batch;

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
		ArrayList fileHandles = new ArrayList();
		Collection fileHandles1 = loadFromSubdirectories(assetsPath, fileHandles);
		Iterator arg1 = fileHandles1.iterator();

		while (arg1.hasNext())
		{
			FileHandle file = (FileHandle) arg1.next();
			assets.load(file.path(), getClassFromPath(file.path()));
		}

		assets.finishLoading();
	}

	private static Collection<FileHandle> loadFromSubdirectories(String path, Collection<FileHandle> fileHandles)
	{
		FileHandle[] files = Gdx.files.internal(path).list();
		FileHandle[] arg5 = files;
		int arg4 = files.length;

		for (int arg3 = 0; arg3 < arg4; ++arg3)
		{
			FileHandle file = arg5[arg3];
			if (file.isDirectory())
			{
				fileHandles = loadFromSubdirectories(file.path(), fileHandles);
			} else
			{
				fileHandles = tryAddingFile(file, fileHandles);
			}
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
		{
			return (Class) classTypes.get(extension);
		} else
		{
			throw new Assets.UnknownExtensionException(extension);
		}
	}

	private static String getExtension(String path)
	{
		int extensionStartIndex = path.lastIndexOf(46) + 1;
		return path.substring(extensionStartIndex);
	}

	public static <T> T get(String fileName)
	{
		return (T) new Texture("assets/monster.png");
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

	public static SpriteBatch getBatch()
	{
		return batch;
	}

	private static class UnknownExtensionException extends GameException
	{
		public UnknownExtensionException(String extension)
		{
			super("Extension " + extension + " is not recognized");
		}
	}
}
