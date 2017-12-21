package pl.mmorpg.prototype.server.states;

import pl.mmorpg.prototype.server.input.InputProcessorAdapter;
import pl.mmorpg.prototype.server.input.KeyHandler;

public class ServerInputHandler extends InputProcessorAdapter
{
	private PlayStateInputActions inputActions;

	public ServerInputHandler(PlayStateInputActions inputActions)
	{
		this.inputActions = inputActions;
	}
	
	@Override
	public boolean scrolled(int amount)
	{
		postponeAction(() -> inputActions.zoomCameraOut(amount*5));
		return super.scrolled(amount);
	}
	
	public class QKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			inputActions.zoomCameraIn();
		}
	}
	
	public class EKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			inputActions.zoomCameraOut();
		}
	}
	
	public class AKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			inputActions.moveCameraLeft();
		}	
	}
	
	public class DKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			inputActions.moveCameraRight();
		}	
	}
	
	public class WKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			inputActions.moveCameraUp();
		}	
	}
	
	public class SKeyHandler implements KeyHandler
	{
		@Override
		public void handle()
		{
			inputActions.moveCameraDown();
		}	
	}
	
}
