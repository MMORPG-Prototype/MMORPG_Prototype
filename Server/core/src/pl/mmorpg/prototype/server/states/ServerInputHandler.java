package pl.mmorpg.prototype.server.states;

import com.badlogic.gdx.InputAdapter;

public class ServerInputHandler extends InputAdapter
{

	private PlayState playState;

	public ServerInputHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public boolean keyDown(int keycode)
	{
		return false;
	}
	
}
