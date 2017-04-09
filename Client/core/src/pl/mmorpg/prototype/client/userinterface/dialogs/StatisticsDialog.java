package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;
import pl.mmorpg.prototype.clientservercommon.CharacterStatsCalculator;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class StatisticsDialog extends Dialog
{
	private UserCharacterDataPacket character;
	private Label levelValueLabel;
	private Label experienceValueLabel;
	private Label maxHitPointsValueLabel;
	private Label maxManaPointsValueLabel;
	private Label strengthValueLabel;
	private Label magicValueLabel;
	private Label dexitirityValueLabel;

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
		levelValueLabel = new Label(character.getLevel().toString(), getSkin());
		text(levelValueLabel).right();
		getContentTable().row();
		text("Experience: ").left();
		experienceValueLabel = new Label(character.getExperience().toString(), getSkin());
		text(experienceValueLabel).right();
		getContentTable().row();
		text("Max hit points: ").left();
		maxHitPointsValueLabel = new Label(CharacterStatsCalculator.getMaxHP(character).toString(), getSkin());
		text(maxHitPointsValueLabel).right();
		getContentTable().row();
		text("Max mana points: ").left();
		maxManaPointsValueLabel = new Label(CharacterStatsCalculator.getMaxMP(character).toString(), getSkin());
		text(maxManaPointsValueLabel).right();
		getContentTable().row();
		text("Strength: ").left();
		strengthValueLabel = new Label(character.getStrength().toString(), getSkin());
		text(strengthValueLabel).right();
		getContentTable().row();
		text("Magic: ").left();
		magicValueLabel = new Label(character.getMagic().toString(), getSkin());
		text(magicValueLabel).right();
		getContentTable().row();
		text("Dexitirity: ").left();
		dexitirityValueLabel = new Label(character.getDexitirity().toString(), getSkin());
		text(dexitirityValueLabel).right();
		getContentTable().row();

		this.setX(0);
		this.setY(280);
		pack();
	}
	
	public void updateStatistics()
	{
		levelValueLabel.setText(character.getLevel().toString());
		experienceValueLabel.setText(character.getExperience().toString());
		maxHitPointsValueLabel.setText(CharacterStatsCalculator.getMaxHP(character).toString());
		maxManaPointsValueLabel.setText(CharacterStatsCalculator.getMaxMP(character).toString());
		strengthValueLabel.setText(character.getStrength().toString());
		magicValueLabel.setText(character.getMagic().toString());
		dexitirityValueLabel.setText(character.getDexitirity().toString());	
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
