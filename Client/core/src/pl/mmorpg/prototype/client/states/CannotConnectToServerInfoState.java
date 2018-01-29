package pl.mmorpg.prototype.client.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.dialogs.ErrorInfoDialog;

public class CannotConnectToServerInfoState implements State
{
	private final Stage stage = Assets.getStage();
	private StateManager states;

	public CannotConnectToServerInfoState(StateManager states)
	{
		this.states = states;
		Gdx.input.setInputProcessor(stage);
		ErrorInfoDialog errorInfoDialog = new ErrorInfoDialog("Cannot connect to server", this::popState);
		errorInfoDialog.show(stage);
	}
	
	private void popState()
	{
		states.pop();
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		stage.draw();
	}

	@Override
	public void update(float deltaTime)
	{
		stage.act();
	}

	@Override
	public void reactivate()
	{
	}

}
