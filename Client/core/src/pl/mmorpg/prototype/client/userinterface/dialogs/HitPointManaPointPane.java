package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class HitPointManaPointPane extends ScrollPane
{
	public HitPointManaPointPane(UserCharacterDataPacket character)
	{
		super(new HitPointManaPointDialog(character));
		initiazlize();
	} 
	
	public HitPointManaPointPane(Integer linkedValueHitPoints, Integer linkedValueManaPoints, int maxHP, int maxMP, String characterName)
	{
		super(new HitPointManaPointDialog(linkedValueHitPoints, linkedValueManaPoints, maxHP, maxMP, characterName));
		initiazlize();
	}

	private void initiazlize()
	{
		this.setHeight(100);
		this.setWidth(200);
		this.setX(0);
		this.setY(Gdx.graphics.getHeight() - this.getHeight());
	}

}
