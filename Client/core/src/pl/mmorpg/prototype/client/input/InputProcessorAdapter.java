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
    	if(shouldAvoidInput())
    		return true;
    	
        KeyHandler keyHandler = getKeyHandler(keycode);
        if (!keyHandler.equals(KeyHandler.EMPTY))
            keyHandlersToActivate.putIfAbsent(keycode, keyHandler);
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
    	if(shouldAvoidInput())
    		return true;
    	
        keyHandlersToActivate.remove(keycode);
        return true;
    }

    public void process()
    {
        activateAll(getActiveKeyHandlers());
    }

    private void activateAll(Collection<KeyHandler> keyHandlers)
    {
        for (KeyHandler keyHandler : keyHandlers)
            keyHandler.handle();
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
    
    protected boolean shouldAvoidInput()
    {
    	return false;
    }
}
