package pl.mmorpg.prototype.client.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import pl.mmorpg.prototype.client.exceptions.GameException;
import pl.mmorpg.prototype.clientservercommon.Settings;

public class Assets
{
	private static String assetsPath = "assets";
	private static Map<String, Class<?>> classTypes = new HashMap<String, Class<?>>();
	private static Set<String> ignoredExtensions = new TreeSet<>();
	private static AssetManager assets = new AssetManager();
	private static BitmapFont font = new BitmapFont();
	private static Map<String, Skin> skins = new HashMap<>();
	private static SpriteBatch batch = new SpriteBatch();
	private static List<CustomStage> stages = new LinkedList<>();

	static
    {
		addClassTypes();
		addIgnoredExtendsions();
		loadSkins(assetsPath);
		loadOthers();
    }

	private static void addClassTypes()
    {
		classTypes.put("png", Texture.class);
        classTypes.put("jpg", Texture.class);
        classTypes.put("bmp", Texture.class);
        classTypes.put("gif", Texture.class);
        classTypes.put("mp3", Music.class);
        classTypes.put("ogg", Sound.class);
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

	private static void loadSkins(String path)
	{
		FileHandle[] files = Gdx.files.internal(path).list();
		for (FileHandle file : files)
		{
			if (file.isDirectory())
				loadSkins(file.path());
			else if (getExtension(file.path()).equals("json"))
				skins.put(file.path(), new Skin(file));
		}

	}

	public static void loadOthers()
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
			else if (!ignoredExtensions.contains(getExtension(file.path())))
				fileHandles.add(file);
		}
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
		T asset;
		try
		{
			asset = assets.get(assetsPath + '/' + fileName);
		} catch (GdxRuntimeException e)
		{
			asset = (T) skins.get(assetsPath + '/' + fileName);
		}
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
				stage.setUsed();
				return stage;
			}
		CustomStage newStage = new CustomStage(new ScalingViewport(Scaling.stretch, Settings.GAME_WIDTH, Settings.GAME_HEIGHT),
				batch);
		newStage.setUsed();
		stages.add(newStage);
		return newStage;
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

	public static class Textures
	{
		public final static String MAIN_CHAR = "MainChar.png";

		public static class Items
		{
			public static final String ITEM_FOLDER = "Items/";
			public static final String SMALL_HEALTH_POTION = ITEM_FOLDER + "SmallManaPotion.png";
		}
	}
}
