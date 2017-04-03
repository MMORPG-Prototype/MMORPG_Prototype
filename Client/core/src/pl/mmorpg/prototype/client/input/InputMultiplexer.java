package pl.mmorpg.prototype.client.input;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;

public class MyInputMultiplexer implements InputProcessor
{

	private List<InputProcessor> processors = new LinkedList<>();

	public MyInputMultiplexer()
	{
	}

	public MyInputMultiplexer(InputProcessor... processors)
	{
		for (int i = 0; i < processors.length; i++)
			this.processors.add(processors[i]);
	}


	public void addProcessor(InputProcessor processor)
	{
		if (processor == null)
			throw new NullPointerException("processor cannot be null");
		processors.add(processor);
	}

	/** @return the number of processors in this multiplexer */
	public int size()
	{
		return processors.size();
	}

	public void clear()
	{
		processors.clear();
	}

	public boolean keyDown(int keycode)
	{
		processors.forEach((processor) -> processor.keyDown(keycode));
		return false;
	}

	public boolean keyUp(int keycode)
	{
		processors.forEach((processor) -> processor.keyUp(keycode));
		return false;
	}

	public boolean keyTyped(char character)
	{
		processors.forEach((processor) -> processor.keyTyped(character));
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		processors.forEach((processor) -> processor.touchDown(screenX, screenY, pointer, button));
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		processors.forEach((processor) -> processor.touchUp(screenX, screenY, pointer, button));
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		processors.forEach((processor) -> processor.touchDragged(screenX, screenY, pointer));
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		processors.forEach((processor) -> processor.mouseMoved(screenX, screenY));
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		processors.forEach((processor) -> processor.scrolled(amount));
		return false;
	}
}
