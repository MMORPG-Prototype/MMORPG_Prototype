package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.states.helpers.CharacterStatsCalculator;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class StatisticsDialog extends Dialog
{
	private UserCharacterDataPacket character;

	public StatisticsDialog(UserCharacterDataPacket character)
	{
		super("Statistics", Settings.DEFAULT_SKIN);
		this.character = character;

		Button closeButton = new CloseButton(this);
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();

		text("Nickname: ").left();
		text(character.getNickname()).right();
		getContentTable().row();
		text("Level: ").left();
		text(character.getLevel().toString()).right();
		getContentTable().row();
		text("Experience: ").left();
		text(character.getExperience().toString()).right();
		getContentTable().row();
		text("Max hit points: ").left();
		text(CharacterStatsCalculator.getMaxHP(character).toString()).right();
		getContentTable().row();
		text("Max mana points: ").left();
		text(CharacterStatsCalculator.getMaxMP(character).toString()).right();
		getContentTable().row();
		text("Strength: ").left();
		text(character.getStrength().toString()).right();
		getContentTable().row();
		text("Magic: ").left();
		text(character.getMagic().toString()).right();
		getContentTable().row();
		text("Dexitirity: ").left();
		text(character.getDexitirity().toString()).right();
		getContentTable().row();

		this.setX(0);
		this.setY(280);
		pack();
	}
	
	void updateStatistics()
	{
		
	}

	
	@Override
	public void setVisible(boolean visible)
	{
		if(visible)
		{
			this.setX(0);
			this.setY(280);
		}
		super.setVisible(visible);
	}
}
