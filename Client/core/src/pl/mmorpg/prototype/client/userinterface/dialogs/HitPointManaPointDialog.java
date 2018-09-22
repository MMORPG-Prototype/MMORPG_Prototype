package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.clientservercommon.CharacterStatsCalculator;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class HitPointManaPointDialog extends Dialog
{
	private final ProgressBar hitPoints;
	private final ProgressBar manaPoints;
	private UserCharacterDataPacket linkedData;

	public HitPointManaPointDialog(UserCharacterDataPacket character)
	{

		super(character.getNickname(), Settings.DEFAULT_SKIN);
		this.linkedData = character;
		hitPoints = new ProgressBar(0, CharacterStatsCalculator.getMaxHP(character), 1.0f, false, Settings.DEFAULT_SKIN);
		hitPoints.setColor(new Color(1.0f, 0.2f, 0.2f, 0.9f));
		manaPoints = new ProgressBar(0, CharacterStatsCalculator.getMaxMp(character), 1.0f, false, Settings.DEFAULT_SKIN);
		manaPoints.setColor(new Color(0.2f, 0.2f, 1.0f, 0.9f));
		
		this.getContentTable().row();
		text("HP: ");
		this.getContentTable().add(hitPoints).top().right();
		this.getContentTable().row();
		text("MP: ");
		this.getContentTable().add(manaPoints).top().right();
		update();
	}

	public void update()
	{
		hitPoints.setValue(linkedData.getHitPoints());
		manaPoints.setValue(linkedData.getManaPoints());
	}
	

}
