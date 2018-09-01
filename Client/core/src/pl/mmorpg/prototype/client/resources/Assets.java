package pl.mmorpg.prototype.client.resources;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
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
import pl.mmorpg.prototype.client.post.proccessing.ShaderBatch;
import pl.mmorpg.prototype.clientservercommon.Settings;

public class Assets
{
	private static Map<String, Class<?>> classTypes;
	private static AssetManager assets;
	private static BitmapFont font;
	private static Map<String, Skin> skins;
	private static SpriteBatch batch;
	private static SpriteBatch backupBatch;
	private static Collection<CustomStage> stages;
	private static LinkedList<Runnable> tasksToExecute = new LinkedList<>();

	public static void loadPreparationSteps()
	{
		tasksToExecute.add(Assets::initializeFields);
		tasksToExecute.add(Assets::loadSeveralStages);
		tasksToExecute.add(Assets::addClassTypes);
		tasksToExecute.add(() -> assets.setLoader(TiledMap.class, new TmxMapLoader()));
		tasksToExecute.add(Assets::loadSkins);
		tasksToExecute.add(Assets::loadOthers);
	}

	public static void executeNextPreparationStep()
	{
		tasksToExecute.removeFirst().run();
	}

	public static boolean preparationFinished()
	{
		return tasksToExecute.isEmpty();
	}

	private static void initializeFields()
	{
		classTypes = new HashMap<>();
		assets = new AssetManager();
		font = new BitmapFont();
		skins = new HashMap<>();
		batch = new ShaderBatch(0.05f, 1.1f);
		backupBatch = new SpriteBatch();
		stages = new LinkedList<>();
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

	private static void loadSkins()
	{
		Set<String> skinPaths = getClasspathResources(Sets.newHashSet("json"));
		for (String path : skinPaths)
			skins.put(path, new Skin(Gdx.files.classpath(path)));
	}

	private static Set<String> getClasspathResources(Set<String> filesExtensions)
	{
		Collection<URL> classpathJars = classpathJars();
		System.out.println(classpathJars.size());
		classpathJars.forEach(System.out::println);
		ConfigurationBuilder configuration = new ConfigurationBuilder()
				.setUrls(classpathJars)
				.setScanners(new ResourcesScanner());
		configuration.setInputsFilter(Assets::isValidResourcePath);
		Reflections reflections = new Reflections(configuration);
		Set<String> resources = reflections.getResources(getResourcePattern(filesExtensions));
		return resources;
	}

	private static Collection<URL> classpathJars()
	{
		String property = System.getProperty("java.class.path");
		System.out.println(property);
		String[] classpathJars = property.split(";");
		String userDir = System.getProperty("user.dir");
		List<URL> result = Arrays.stream(classpathJars)
				.map(jar -> toURL(userDir, jar))
				.collect(Collectors.toList());
		return result;
	}

	private static URL toURL(String userDir, String jar)
	{
		try
		{
			String path = jar.trim().replace(" ", "%20");
			if (!new File(path).isAbsolute())
				return new URL("file:\\" + userDir + '\\' + path);

			return new URL("file:\\" + path);
		} catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static Pattern getResourcePattern(Set<String> filesExtensions)
	{
		return Pattern.compile(".+\\." + getCombinedExtensions(filesExtensions));
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

	private static boolean isValidResourcePath(String resourcePath)
	{
		return !resourcePath.startsWith("com.") &&
				!resourcePath.startsWith("org.") &&
				!resourcePath.startsWith("junit.") &&
				!resourcePath.startsWith("lombok.") &&
				!resourcePath.startsWith("javax.") &&
				!resourcePath.startsWith("sun.") &&
				(!resourcePath.startsWith("UISkins.") || resourcePath.endsWith(".json")) &&
				!resourcePath.contains("/");
	}

	public static void loadOthers()
	{
		Set<String> resourcesPaths = getClasspathResources(classTypes.keySet());

		for (String path : resourcesPaths)
			assets.load(path, getClassFromPath(path));
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

	public static float getLoadingProgress()
	{
		return assets.getProgress();
	}

	public static void update()
	{
		assets.update(10);
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
		return new CustomStage(new ScalingViewport(Scaling.stretch, Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT));
	}

	public static SpriteBatch getBatch()
	{
		return batch;
	}

	public static SpriteBatch getBackupBatch()
	{
		return backupBatch;
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
