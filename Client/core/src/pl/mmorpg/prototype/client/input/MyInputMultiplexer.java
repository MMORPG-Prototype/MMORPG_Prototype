package pl.mmorpg.prototype.client.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

public class MyInputMultiplexer implements InputProcessor
{

	private Array<InputProcessor> processors = new Array(4);

	public MyInputMultiplexer()
	{
	}

	public MyInputMultiplexer(InputProcessor... processors)
	{
		for (int i = 0; i < processors.length; i++)
			this.processors.add(processors[i]);
	}

	public void addProcessor(int index, InputProcessor processor)
	{
		if (processor == null)
			throw new NullPointerException("processor cannot be null");
		processors.insert(index, processor);
	}

	public void removeProcessor(int index)
	{
		processors.removeIndex(index);
	}

	public void addProcessor(InputProcessor processor)
	{
		if (processor == null)
			throw new NullPointerException("processor cannot be null");
		processors.add(processor);
	}

	public void removeProcessor(InputProcessor processor)
	{
		processors.removeValue(processor, true);
	}

	/** @return the number of processors in this multiplexer */
	public int size()
	{
		return processors.size;
	}

	public void clear()
	{
		processors.clear();
	}

	public void setProcessors(Array<InputProcessor> processors)
	{
		this.processors = processors;
	}

	public Array<InputProcessor> getProcessors()
	{
		return processors;
	}

	public boolean keyDown(int keycode)
	{
		for (int i = 0, n = processors.size; i < n; i++)
			processors.get(i).keyDown(keycode);

		return false;
	}

	public boolean keyUp(int keycode)
	{
		for (int i = 0, n = processors.size; i < n; i++)
			processors.get(i).keyUp(keycode);

		return false;
	}

	public boolean keyTyped(char character)
	{
		for (int i = 0, n = processors.size; i < n; i++)
			processors.get(i).keyTyped(character);

		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		for (int i = 0, n = processors.size; i < n; i++)
			processors.get(i).touchDown(screenX, screenY, pointer, button);
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		for (int i = 0, n = processors.size; i < n; i++)
			processors.get(i).touchUp(screenX, screenY, pointer, button);

		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		for (int i = 0, n = processors.size; i < n; i++)
			processors.get(i).touchDragged(screenX, screenY, pointer);

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		for (int i = 0, n = processors.size; i < n; i++)
			processors.get(i).mouseMoved(screenX, screenY);

		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		for (int i = 0, n = processors.size; i < n; i++)
			processors.get(i).scrolled(amount);

		return false;
	}
}
