package pl.mmorpg.prototype.client.states;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.exceptions.NoSuchStateTypeException;

public class StateManager
{
    private Stack<State> states = new Stack<State>();

	public State usedState()
    {
        return states.peek();
    }

	public void update(float deltaTime)
	{
		usedState().update(deltaTime);
	}

    public void render(SpriteBatch batch)
    {
        usedState().render(batch);
    }

    public void set(State state)
    {
        states.pop();
        states.push(state);
    }

    public void push(State state)
    {
        states.push(state);
    }

    public void pop()
    {
        states.pop();
		usedState().reactivate();
        if (states.empty())
            Gdx.app.exit();
    }

    public boolean empty()
    {
        return states.empty();
    }

	public <T> T find(Class<T> stateType)
	{
		for (State state : states)
			if (state.getClass().equals(stateType))
				return (T) state;
		throw new NoSuchStateTypeException();
	}

}
