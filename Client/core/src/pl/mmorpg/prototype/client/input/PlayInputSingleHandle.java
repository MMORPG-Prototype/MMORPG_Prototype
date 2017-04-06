package pl.mmorpg.prototype.client.input;

public class PlayInputSingleHandle extends InputProcessorAdapter
{
	private ActorManipulator dialogManipulator;

	public PlayInputSingleHandle(ActorManipulator dialogManipulator)
	{
		this.dialogManipulator = dialogManipulator;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		if (dialogManipulator.isMapped(keycode))
			dialogManipulator.showOrHide(keycode);
		return false;
	}

}
