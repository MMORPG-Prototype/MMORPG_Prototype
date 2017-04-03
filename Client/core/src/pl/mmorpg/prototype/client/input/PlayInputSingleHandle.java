package pl.mmorpg.prototype.client.input;

public class PlayInputSingleHandle extends InputProcessorAdapter
{
	private DialogManipulator dialogManipulator;

	public PlayInputSingleHandle(DialogManipulator dialogManipulator)
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
