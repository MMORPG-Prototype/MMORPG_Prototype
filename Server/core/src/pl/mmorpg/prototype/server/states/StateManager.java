package pl.mmorpg.prototype.server.states;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

public class StateManager
{
    private Stack<State> states = new Stack<State>();

	public void update(float deltaTime)
    {
		usedState().update(deltaTime);
    }

    private State usedState()
    {
        return states.peek();
    }

    public void render(Batch batch)
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
        if (states.empty())
            Gdx.app.exit();
    }

    public boolean empty()
    {
        return states.empty();
    }

}
