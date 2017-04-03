package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.states.ChoosingCharacterState;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class ChoosingCharacterDialog extends Dialog
{
	private ChoosingCharacterState linkedState;
	private ButtonGroup<CheckBox> checkBoxes = new ButtonGroup<>();
	private UserCharacterDataPacket[] characters;

	public ChoosingCharacterDialog(ChoosingCharacterState linkedState, UserCharacterDataPacket[] characters)
	{
		super("Choose character", Settings.DEFAULT_SKIN);
		this.linkedState = linkedState;
		this.characters = characters;

		text("Username");
		text("Level");
		text("Choose");
		getContentTable().row();
		for (UserCharacterDataPacket character : characters)
		{
			text(character.nickname);
			text(character.level.toString());
			CheckBox checkBox = new CheckBox("", Settings.DEFAULT_SKIN);
			checkBox.setName(character.nickname);
			checkBoxes.add(checkBox);
			getContentTable().add(checkBox);
			getContentTable().row();
		}

		button("Ok", DialogResults.OK);
		button("Cancel", DialogResults.CANCEL);
		getContentTable().row();
	}

	@Override
	protected void result(Object object)
	{
		if(object.equals(DialogResults.OK))
			linkedState.characterChoosen(characters[checkBoxes.getCheckedIndex()]);
		else
			Gdx.app.exit();
	}

}
