package pl.mmorpg.prototype.client.userinterface.dialogs.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ActorPopUpHideListener extends InputListener
{
	private Actor actorToShow;
	private Stage linkedStage;

	public ActorPopUpHideListener(Stage linkedStage, Actor actorToShow)
	{
		this.linkedStage = linkedStage;
		this.actorToShow = actorToShow;
	}
	
	@Override
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
	{
		actorToShow.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY() + 10);
		linkedStage.addActor(actorToShow);
	}

	@Override
	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
	{
		actorToShow.remove();
	}
}
