package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import org.apache.commons.lang3.StringUtils;
import pl.mmorpg.prototype.client.objects.Player;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.LevelUpProgressBar;
import pl.mmorpg.prototype.clientservercommon.StatisticsCalculator;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.Statistics;

public class StatisticsDialog extends Dialog
{
	private final Player player;
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

	public StatisticsDialog(Player player, Stage containerForPopUpDialog)
	{
		super("Statistics", Settings.DEFAULT_SKIN);
		this.player = player;

		Button closeButton = new CloseButton(this);
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();

		UserCharacterDataPacket playerData = player.getData();
		text("Nickname: ").left();
		text(playerData.getNickname()).right();
		getContentTable().row();
		text("Level: ").left();
		levelValueLabel = new Label(playerData.getLevel().toString(), getSkin());
		text(levelValueLabel).right();
		getContentTable().row();
		text("Experience: ").left();
		experienceValueLabel = new Label(playerData.getExperience().toString(), getSkin());
		text(experienceValueLabel).right();
		getContentTable().row();
		levelUpProgressBar = new LevelUpProgressBar(playerData.getExperience(), containerForPopUpDialog);
		getContentTable().add(levelUpProgressBar).left();
		levelUpProgressBarPercentsLabel = new Label(formatPercents(levelUpProgressBar.getPercent()), getSkin());
		text(levelUpProgressBarPercentsLabel).right();
		getContentTable().row();
		text("Max hit points: ").left();
		maxHitPointsValueLabel = new Label(getMaxHp().toString(), getSkin());
		text(maxHitPointsValueLabel).right();
		getContentTable().row();
		text("Max mana points: ").left();
		maxManaPointsValueLabel = new Label(getMaxMp().toString(), getSkin());
		text(maxManaPointsValueLabel).right();
		getContentTable().row();
		text("Strength: ").left();
		strengthValueLabel = new Label(playerData.getStrength().toString(), getSkin());
		text(strengthValueLabel).right();
		getContentTable().row();
		text("Intelligence: ").left();
		intelligenceValueLabel = new Label(playerData.getIntelligence().toString(), getSkin());
		text(intelligenceValueLabel).right();
		getContentTable().row();
		text("Dexterity: ").left();
		dexterityValueLabel = new Label(playerData.getDexterity().toString(), getSkin());
		text(dexterityValueLabel).right();
		getContentTable().row();
		text("Level up points: ").left();
		levelUpPointsLabel = new Label(playerData.getLevelUpPoints().toString(), getSkin());
		text(levelUpPointsLabel).right();
		getContentTable().row();

		this.setX(0);
		this.setY(280);
		pack();
	}

	private Integer getMaxHp()
	{
		return StatisticsCalculator.calculateMaxHp(player.getProperties());
	}

	private Integer getMaxMp()
	{
		return StatisticsCalculator.calculateMaxMp(player.getProperties());
	}
	
	public void updateStatistics()
	{
		MonsterProperties properties = player.getProperties();
		Statistics statistics = player.getStatistics();
		levelValueLabel.setText(String.valueOf(properties.level));
		experienceValueLabel.setText(String.valueOf(properties.experience));
		maxHitPointsValueLabel.setText(String.valueOf(statistics.maxHp));
		maxManaPointsValueLabel.setText(String.valueOf(statistics.maxMp));
		strengthValueLabel.setText(String.valueOf(properties.strength));
		intelligenceValueLabel.setText(String.valueOf(properties.intelligence));
		dexterityValueLabel.setText(String.valueOf(properties.dexterity));
		levelUpPointsLabel.setText(String.valueOf(player.getData().getLevelUpPoints()));
		levelUpProgressBar.updateExperience(properties.experience);
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
