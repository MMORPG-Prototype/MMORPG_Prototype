package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class HitPointManaPointPane extends ScrollPane
{
	private static HitPointManaPointDialog dialogInitializer;
	private final HitPointManaPointDialog dialog;
	
	public HitPointManaPointPane(UserCharacterDataPacket character)
	{
		super(createDialog(character));
		dialog = dialogInitializer;
		initiazlize();
	} 
	
	public HitPointManaPointPane(Integer linkedValueHitPoints, Integer linkedValueManaPoints, int maxHP, int maxMP, String characterName)
	{
		super(createDialog(linkedValueHitPoints, linkedValueManaPoints, maxHP, maxMP, characterName));
		dialog = dialogInitializer;
		initiazlize();
	}
	
	private static HitPointManaPointDialog createDialog(UserCharacterDataPacket character)
	{
		dialogInitializer = new HitPointManaPointDialog(character);
		return dialogInitializer;
	}
	
	private static HitPointManaPointDialog createDialog(Integer linkedValueHitPoints, Integer linkedValueManaPoints, int maxHP, int maxMP, String characterName)
	{
		dialogInitializer = new HitPointManaPointDialog(linkedValueHitPoints, linkedValueManaPoints, maxHP, maxMP, characterName);
		return dialogInitializer;
	}

	private void initiazlize()
	{
		this.setHeight(100);
		this.setWidth(200);
		this.setX(0);
		this.setY(Gdx.graphics.getHeight() - this.getHeight());
	}

	public void updateValues()
	{
		dialog.update();
		
	}

}
