package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.levelup.*;

public class LevelUpDialog extends Dialog
{
	private final UserCharacterDataPacket character;
	private final UserInterface linkedInterface;
	private final Label levelUpPointsValueLabel;
	private final Label strengthValueLabel;
	private final Label intelligenceValueLabel;
	private final Label dexterityValueLabel;

	public LevelUpDialog(UserInterface linkedInterface, UserCharacterDataPacket character)
	{
		super("You have leveled up!", Settings.DEFAULT_SKIN);
		this.linkedInterface = linkedInterface;
		this.character = character;

		Button closeButton = new CloseButton(this);
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();

		getContentTable().row();
		text("Strength: ").left();
		strengthValueLabel = new Label(character.getStrength().toString(), getSkin());
		text(strengthValueLabel).right();

		Button strengthAddButton = new Button(Settings.DEFAULT_SKIN, "maximize");
		strengthAddButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				strengthAddButtonClicked();
			}
		});

		getContentTable().add(strengthAddButton);
		getContentTable().row();
		text("Intelligence: ").left();
		intelligenceValueLabel = new Label(character.getIntelligence().toString(), getSkin());
		text(intelligenceValueLabel).right();

		Button manaAddButton = new Button(Settings.DEFAULT_SKIN, "maximize");
		manaAddButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				manaAddButtonClicked();
			}
		});
		getContentTable().add(manaAddButton);
		getContentTable().row();
		text("Dexterity: ").left();
		dexterityValueLabel = new Label(character.getDexterity().toString(), getSkin());
		text(dexterityValueLabel).right();

		Button dexterityAddButton = new Button(Settings.DEFAULT_SKIN, "maximize");
		dexterityAddButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				dexterityAddButtonClicked();
			}
		});
		getContentTable().add(dexterityAddButton);
		getContentTable().row();
		text("Points to spend: ").left();
		levelUpPointsValueLabel = new Label(String.valueOf(character.getLevelUpPoints()), getSkin());
		text(levelUpPointsValueLabel).right();
		pack();
	}

	private void strengthAddButtonClicked()
	{
		if (character.getLevelUpPoints() > 0)
			linkedInterface.userDistributedLevelUpPoint(new UseLevelUpPointOnStrengthPacket());
	}

	private void manaAddButtonClicked()
	{
		if (character.getLevelUpPoints() > 0)
			linkedInterface.userDistributedLevelUpPoint(new UseLevelUpPointOnIntelligencePacket());
	}

	private void dexterityAddButtonClicked()
	{
		if (character.getLevelUpPoints() > 0)
			linkedInterface.userDistributedLevelUpPoint(new UseLevelUpPointOnDexterityPacket());
	}


	public void updateShownValues()
	{
		strengthValueLabel.setText(character.getStrength().toString());
		intelligenceValueLabel.setText(character.getIntelligence().toString());
		dexterityValueLabel.setText(character.getDexterity().toString());
		levelUpPointsValueLabel.setText(String.valueOf(character.getLevelUpPoints()));
	}


}
