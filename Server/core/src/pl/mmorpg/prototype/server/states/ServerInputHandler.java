package pl.mmorpg.prototype.server.states;

import com.badlogic.gdx.Input.Keys;
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
		if(keycode == Keys.D)
			playState.addGreenDragon();
		else if(keycode == Keys.R)
			playState.addRedDragon();
		return false;
	}
	
}
