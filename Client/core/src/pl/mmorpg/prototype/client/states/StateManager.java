package pl.mmorpg.prototype.client.states;

import java.util.Optional;
import java.util.Stack;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.exceptions.GameException;
import pl.mmorpg.prototype.client.exceptions.NoSuchStateTypeException;

public class StateManager
{
	private Stack<State> states = new Stack<>();
	private Consumer<State> disposeState;

	public StateManager(Consumer<State> disposeState)
	{
		this.disposeState = disposeState;
	}

	public synchronized State usedState()
	{
		return states.peek();
	}

	public synchronized void update(float deltaTime)
	{
		usedState().update(deltaTime);
	}

	public synchronized void render(SpriteBatch batch)
	{
		usedState().render(batch);
	}

	public synchronized void set(State state)
	{
		State removedState = states.pop();
		disposeState.accept(removedState);
		states.push(state);
	}

	public synchronized void push(State state)
	{
		states.push(state);
	}

	public synchronized void pop()
	{
		states.pop();
		usedState().reactivate();
		if (states.empty())
			Gdx.app.exit();
	}

	public synchronized boolean empty()
	{
		return states.empty();
	}

	@SuppressWarnings(value = "unchecked")
	public synchronized <T> T find(Class<T> stateType)
	{
		return findOptionally(stateType)
				.orElseThrow(NoSuchStateTypeException::new);
	}

	private <T> Optional<T> findOptionally(Class<T> stateType)
	{
		for (State state : states)
			if (state.getClass().equals(stateType))
				return Optional.of((T) state);
		return Optional.empty();
	}
}
