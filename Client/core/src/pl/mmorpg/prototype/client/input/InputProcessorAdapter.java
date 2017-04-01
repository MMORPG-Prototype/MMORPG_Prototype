package pl.mmorpg.prototype.client.input;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class InputProcessorAdapter extends InputAdapter
{
    private Map<String, KeyHandler> keyHandlers;
    protected Map<Integer, KeyHandler> keyHandlersToActivate = Collections
            .synchronizedMap(new HashMap<Integer, KeyHandler>());

    public InputProcessorAdapter()
    {
        keyHandlers = new KeyHandlerFactory(this).produceAll();
    }

    @Override
    public boolean keyDown(int keycode)
    {
        KeyHandler keyHandler = getKeyHandler(keycode);
        if (!keyHandler.equals(KeyHandler.EMPTY))
            keyHandlersToActivate.putIfAbsent(keycode, keyHandler);
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        keyHandlersToActivate.remove(keycode);
        return true;
    }

    public void process()
    {
        activateAll(getActiveKeyHandlers());
    }

    protected void activateAll(Collection<KeyHandler> keyHandlers)
    {
        for (KeyHandler keyHandler : keyHandlers)
            keyHandler.handle();
    }

    public Collection<KeyHandler> getActiveKeyHandlers()
    {
        return keyHandlersToActivate.values();
    }

    public KeyHandler getKeyHandler(int key)
    {
        KeyHandler keyHandler = keyHandlers.get(Keys.toString(key));
        return keyHandler;
    }

    protected KeyHandler produceKeyHandler(int key)
    {
        return new KeyHandlerFactory(this).produce(key);
    }

    public void simulateKeyUpInput(int keyCode)
    {
        keyUp(keyCode);
    }

    public void simulateKeyDownInput(int keyCode)
    {
        keyDown(keyCode);
    }

}
