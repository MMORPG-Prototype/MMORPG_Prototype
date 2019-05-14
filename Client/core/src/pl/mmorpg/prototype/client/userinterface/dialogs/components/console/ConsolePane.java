package pl.mmorpg.prototype.client.userinterface.dialogs.components.console;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public class ConsolePane extends ScrollPane
{
	private final ConsoleList consoleList;
	private final Runnable scrollingTask = () -> setScrollPercentY(1.0f);

	public ConsolePane()
	{
		super(new ConsoleList());
		consoleList = (ConsoleList) getActor();
		setSmoothScrolling(false);  
		setTransform(true); 
	}

	public void addMessage(String message)
	{
		consoleList.addMessage(message);
		Gdx.app.postRunnable(scrollingTask);
	}
	
	public void addErrorMessage(String message)
	{
		consoleList.addMessage(message, Color.RED);
		Gdx.app.postRunnable(scrollingTask);
	}
}
