package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import org.apache.commons.lang3.StringUtils;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.LevelUpProgressBar;
import pl.mmorpg.prototype.clientservercommon.CharacterStatsCalculator;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class StatisticsDialog extends Dialog
{
	private final UserCharacterDataPacket character;
	private final Label levelValueLabel;
	private final Label experienceValueLabel;
	private final LevelUpProgressBar levelUpProgressBar;
	private final Label levelUpProgressBarPercentsLabel;
	private final Label maxHitPointsValueLabel;
	private final Label maxManaPointsValueLabel;
	private final Label strengthValueLabel;
	private final Label intelligenceValueLabel;
	private final Label dexterityValueLabel;
	private final Label levelUpPointsLabel;

	public StatisticsDialog(UserCharacterDataPacket character, Stage containerForPopUpDialog)
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
		levelUpProgressBar = new LevelUpProgressBar(character.getExperience(), containerForPopUpDialog);
		getContentTable().add(levelUpProgressBar).left();
		levelUpProgressBarPercentsLabel = new Label(formatPercents(levelUpProgressBar.getPercent()), getSkin());
		text(levelUpProgressBarPercentsLabel).right();
		getContentTable().row();
		text("Max hit points: ").left();
		maxHitPointsValueLabel = new Label(CharacterStatsCalculator.getMaxHP(character).toString(), getSkin());
		text(maxHitPointsValueLabel).right();
		getContentTable().row();
		text("Max mana points: ").left();
		maxManaPointsValueLabel = new Label(CharacterStatsCalculator.getMaxMp(character).toString(), getSkin());
		text(maxManaPointsValueLabel).right();
		getContentTable().row();
		text("Strength: ").left();
		strengthValueLabel = new Label(character.getStrength().toString(), getSkin());
		text(strengthValueLabel).right();
		getContentTable().row();
		text("Intelligence: ").left();
		intelligenceValueLabel = new Label(character.getIntelligence().toString(), getSkin());
		text(intelligenceValueLabel).right();
		getContentTable().row();
		text("Dexterity: ").left();
		dexterityValueLabel = new Label(character.getDexterity().toString(), getSkin());
		text(dexterityValueLabel).right();
		getContentTable().row();
		text("Level up points: ").left();
		levelUpPointsLabel = new Label(character.getLevelUpPoints().toString(), getSkin());
		text(levelUpPointsLabel).right();
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
		maxManaPointsValueLabel.setText(CharacterStatsCalculator.getMaxMp(character).toString());
		strengthValueLabel.setText(character.getStrength().toString());
		intelligenceValueLabel.setText(character.getIntelligence().toString());
		dexterityValueLabel.setText(character.getDexterity().toString());
		levelUpPointsLabel.setText(character.getLevelUpPoints().toString());
		levelUpProgressBar.updateExperience(character.getExperience());
		levelUpProgressBarPercentsLabel.setText(formatPercents(levelUpProgressBar.getPercent()));
	}

	private String formatPercents(float fromZeroToOneValue)
	{
		return StringUtils.substring(String.valueOf(fromZeroToOneValue*100), 0, 4) + '%';
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
