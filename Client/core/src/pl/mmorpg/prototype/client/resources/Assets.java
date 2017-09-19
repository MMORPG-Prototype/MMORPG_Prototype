package pl.mmorpg.prototype.client.resources;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.google.common.collect.Sets;

import pl.mmorpg.prototype.client.exceptions.GameException;
import pl.mmorpg.prototype.clientservercommon.Settings;

public class Assets
{
	private static Map<String, Class<?>> classTypes = new HashMap<String, Class<?>>();
	private static Set<String> ignoredExtensions = new TreeSet<>();
	private static AssetManager assets = new AssetManager();
	private static BitmapFont font = new BitmapFont();
	private static Map<String, Skin> skins = new HashMap<>();
	private static SpriteBatch batch = new SpriteBatch();
	private static List<CustomStage> stages = new LinkedList<>();

	static
	{
		loadSeveralStages();
		assets.setLoader(TiledMap.class, new TmxMapLoader());
		addClassTypes();
		addIgnoredExtendsions();
		loadSkins();
		loadOthers();
	}

	private static void loadSeveralStages()
	{
		for (int i = 0; i < 7; i++)
		{
			CustomStage stage = createStage();
			stage.setUsed(false);
			stages.add(stage);
		}
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

	private static void addIgnoredExtendsions()
	{
		ignoredExtensions.add("atlas");
		ignoredExtensions.add("fnt");
		ignoredExtensions.add("txt");
		ignoredExtensions.add("md");
		ignoredExtensions.add("gitignore");
		ignoredExtensions.add("lml");
		ignoredExtensions.add("usl");
		ignoredExtensions.add("xcf");
		ignoredExtensions.add("svg");
		ignoredExtensions.add("ttf");
		ignoredExtensions.add("hiero");
		ignoredExtensions.add("json");
	}

	private static void loadSkins()
	{
		Set<String> skinPaths = getClasspathResources(Sets.newHashSet("json"));
		for (String path : skinPaths)
			skins.put(path, new Skin(Gdx.files.classpath(path)));

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

	public static void loadOthers()
	{
		Set<String> resourcesPaths = getClasspathResources(classTypes.keySet());

		for (String path : resourcesPaths)
			assets.load(path, getClassFromPath(path));

		assets.finishLoading();
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
		T asset;
		try
		{
			asset = assets.get(fileName);
		} catch (GdxRuntimeException e)
		{
			asset = (T) skins.get(fileName);
		}
		if (asset == null)
			throw new UnloadedAssetException(fileName);
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
		stages.clear();
		batch.dispose();
		for (Skin skin : skins.values())
			skin.dispose();
	}

	public static BitmapFont getFont()
	{
		return font;
	}

	public static Stage getStage()
	{
		for (CustomStage stage : stages)
			if (!stage.isUsed())
			{
				stage.setUsed(true);
				return stage;
			}
		CustomStage newStage = createStage();
		newStage.setUsed(true);
		stages.add(newStage);
		return newStage;
	}

	private static CustomStage createStage()
	{
		return new CustomStage(new ScalingViewport(Scaling.stretch, Settings.GAME_WIDTH, Settings.GAME_HEIGHT), batch);
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

	private static class UnloadedAssetException extends GameException
	{
		public UnloadedAssetException(String fileName)
		{
			super("Unloaded resource: " + fileName);
		}
	}

	public static class Textures
	{
		public final static String MAIN_CHAR = "MainChar.png";

		public static class Items
		{
			public static final String ITEM_FOLDER = "Items/";
			public static final String SMALL_HEALTH_POTION = ITEM_FOLDER + "SmallHealthPotion.png";
			public static final String SMALL_MANA_POTION = ITEM_FOLDER + "SmallManaPotion.png";
		}

		public static class Map
		{
			public static final String MAP_FOLDER = "MAP/";
			public static final String GREEN_GRASS = MAP_FOLDER + "greenGrass0.png";
		}
	}
}
