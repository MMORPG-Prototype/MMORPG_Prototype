package pl.mmorpg.prototype.server.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import pl.mmorpg.prototype.server.ServerSettings;
import pl.mmorpg.prototype.server.exceptions.GameException;
import pl.mmorpg.prototype.server.headless.NullBatch;

public class Assets
{
	private static Map<String, Class<?>> classTypes = new HashMap<String, Class<?>>();
	private static AssetManager assets = new AssetManager();
	private static BitmapFont font = new BitmapFont();
	private static Batch batch;

	static
	{
		if (ServerSettings.isHeadless)
			batch = new NullBatch();
		else
			batch = new SpriteBatch();
	}

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
		Set<String> files = getClasspathResources(classTypes.keySet());
		for (String file : files)
			assets.load(file, getClassFromPath(file));

		assets.finishLoading();
	}
	
	private static Set<String> getClasspathResources(Set<String> filesExtensions)
	{
		return new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forManifest())
				.setScanners(new ResourcesScanner()))
					.getResources(Pattern.compile("(.*?)." + getCombinedExtensions(filesExtensions)));
	}
	
	private static String getCombinedExtensions(Set<String> filesExtensions)
	{
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append('(');
		String joinedExtensions = filesExtensions.stream()
		.collect(Collectors.joining("|"));
		strBuilder.append(joinedExtensions);
		strBuilder.append(')');
		return strBuilder.toString();
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
		T asset = assets.get(fileName);
		return asset;
	}

	public static <T> T get(String fileName, Class<T> classType)
	{
		return assets.get(fileName, classType);
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

	public static Batch getBatch()
	{
		return batch;
	}

}
