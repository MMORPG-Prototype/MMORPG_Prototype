package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.clientservercommon.CharacterStatsCalculator;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class HitPointManaPointDialog extends Dialog
{
	private final Integer linkedValueManaPoints;
	private final Integer linkedValueHitPoints;
	private final ProgressBar hitPoints;
	private final ProgressBar manaPoints;

	public HitPointManaPointDialog(UserCharacterDataPacket character)
	{
		this(character.getHitPoints(), character.getManaPoints(), 
				CharacterStatsCalculator.getMaxHP(character),
				CharacterStatsCalculator.getMaxMP(character),
				character.getNickname());
	}

	public HitPointManaPointDialog(Integer linkedValueHitPoints, Integer linkedValueManaPoints, int maxHP, int maxMP, String characterName)
	{
		super(characterName, Settings.DEFAULT_SKIN);
		this.linkedValueHitPoints = linkedValueHitPoints;
		this.linkedValueManaPoints = linkedValueManaPoints;
		hitPoints = new ProgressBar(0, maxHP, 1.0f, false, Settings.DEFAULT_SKIN);
		hitPoints.setColor(new Color(1.0f, 0.2f, 0.2f, 0.9f));
		manaPoints = new ProgressBar(0, maxMP, 1.0f, false, Settings.DEFAULT_SKIN);
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
		hitPoints.setValue(linkedValueHitPoints);
		manaPoints.setValue(linkedValueManaPoints);
	}
	

}
