package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class LevelUpDialog extends Dialog
{
	private final UserCharacterDataPacket character;
	private final UserInterface linkedInterface;
	private final Label pointsValueLabel;
	private final Label strengthValueLabel;
	private final Label magicValueLabel;
	private final Label dexitirityValueLabel;
	private int statPoints = 10;

	public LevelUpDialog(UserInterface linkedInterface, UserCharacterDataPacket character)
	{
		super("You have leveld up!", Settings.DEFAULT_SKIN);
		this.linkedInterface = linkedInterface;
		this.character = character;

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
		text("Magic: ").left();
		magicValueLabel = new Label(character.getMagic().toString(), getSkin());
		text(magicValueLabel).right();
		
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
		text("Dexitirity: ").left();
		dexitirityValueLabel = new Label(character.getDexitirity().toString(), getSkin());
		text(dexitirityValueLabel).right();
		
		Button dexitirityAddButton = new Button(Settings.DEFAULT_SKIN, "maximize");
		dexitirityAddButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				dexitirityAddButtonClicked();
			}
		});
		getContentTable().add(dexitirityAddButton);
		getContentTable().row();
		text("Points to spend: ").left();
		pointsValueLabel = new Label(String.valueOf(statPoints), getSkin());
		text(pointsValueLabel).right();
		pack();
	}
	
	private void strengthAddButtonClicked()
	{
		character.setStrength(character.getStrength() + 1);
		userPressedAddButton();
	}
	
	private void dexitirityAddButtonClicked()
	{
		character.setDexitirity(character.getDexitirity() + 1);
		userPressedAddButton();
	}
	
	private void manaAddButtonClicked()
	{
		character.setMagic(character.getMagic() + 1);
		userPressedAddButton();
	}

	private void userPressedAddButton()
	{
		statPoints--;
		if(statPoints <= 0)
		{
			linkedInterface.userDistributedStatPoints();
			pointsValueLabel.setText(String.valueOf(statPoints));
			this.remove();
		}
		updateShownValues();
	}

	private void updateShownValues()
	{
		strengthValueLabel.setText(character.getStrength().toString());
		magicValueLabel.setText(character.getMagic().toString());
		dexitirityValueLabel.setText(character.getDexitirity().toString());
		pointsValueLabel.setText(String.valueOf(statPoints));
	}

}
