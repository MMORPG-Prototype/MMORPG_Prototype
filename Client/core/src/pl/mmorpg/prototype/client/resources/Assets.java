package pl.mmorpg.prototype.client.resources;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import pl.mmorpg.prototype.client.exceptions.GameException;

public class Assets
{
    private String assetsPath = "assets";
    private Map<String, Class<?>> classTypes = new HashMap<String, Class<?>>();
    private AssetManager assets = new AssetManager();
    private BitmapFont font = new BitmapFont();

    public Assets()
    {
        addClassTypes();
        loadAll();
    }

    private void addClassTypes()
    {
        classTypes.put("png", Texture.class);
        classTypes.put("jpg", Texture.class);
        classTypes.put("bmp", Texture.class);
        classTypes.put("gif", Texture.class);
        classTypes.put("mp3", Music.class);
        classTypes.put("ogg", Sound.class);
    }

    public void loadAll()
    {
        FileHandle[] files = Gdx.files.internal(assetsPath).list();
        for (FileHandle file : files)
            assets.load(file.path(), getClassFromPath(file.path()));

        assets.finishLoading();
    }

    private Class<?> getClassFromPath(String path)
    {
        String extension = getExtension(path);
        if (classTypes.containsKey(extension))
            return classTypes.get(extension);
        else
            throw new UnknownExtensionException(extension);
    }

    private String getExtension(String path)
    {
        int extensionStartIndex = path.lastIndexOf('.') + 1;
        return path.substring(extensionStartIndex);
    }

    public <T> T get(String fileName)
    {
        T asset = assets.get(assetsPath + '/' + fileName);
        return asset;
    }

    public <T> T get(String fileName, Class<T> classType)
    {
        return assets.get(assetsPath + '/' + fileName, classType);
    }

    public void dispose()
    {
        assets.dispose();
        font.dispose();
    }

    public BitmapFont getFont()
    {
        return font;
    }

    private class UnknownExtensionException extends GameException
    {
        public UnknownExtensionException(String extension)
        {
            super("Extension " + extension + "is not recognized");
        }
    }

}
