package pl.mmorpg.prototype.server.input;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputProcessorAdapter implements InputProcessor
{
    private final Map<String, KeyHandler> keyHandlers;
    private final Map<Integer, KeyHandler> keyHandlersToActivate = Collections
            .synchronizedMap(new HashMap<Integer, KeyHandler>());
    private final LinkedBlockingQueue<Runnable> postponedActions = new LinkedBlockingQueue<>();

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

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }

    public void process()
    {
        activateAll(getActiveKeyHandlers());
        while(!postponedActions.isEmpty())
        	postponedActions.poll().run();
    }

    private void activateAll(Collection<KeyHandler> keyHandlers)
    {
        for (KeyHandler keyHandler : keyHandlers)
            keyHandler.handle();
    }
    
    protected void postponeAction(Runnable action)
    {
    	postponedActions.offer(action);
    }

    private Collection<KeyHandler> getActiveKeyHandlers()
    {
        return keyHandlersToActivate.values();
    }

    private KeyHandler getKeyHandler(int key)
    {
        KeyHandler keyHandler = keyHandlers.get(Keys.toString(key));
        return keyHandler;
    }
    

}
