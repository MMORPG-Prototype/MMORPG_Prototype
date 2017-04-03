package pl.mmorpg.prototype.client.states.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class StatisticsDialog extends Dialog
{
	private UserCharacterDataPacket character;

	public StatisticsDialog(UserCharacterDataPacket character)
	{
		super("Statistics", Settings.DEFAULT_SKIN);
		this.character = character;
		text("Nickname: ").left();
		text(character.nickname).right();
		getContentTable().row();
		text("Level: ").left();
		text(character.level.toString()).right();
		getContentTable().row();
		text("Experience: ").left();
		text(character.experience.toString()).right();
		getContentTable().row();
		text("Strength: ").left();
		text(character.strength.toString()).right();
		getContentTable().row();
		text("Magic: ").left();
		text(character.magic.toString()).right();
		getContentTable().row();
		text("Dexitirity: ").left();
		text(character.dexitirity.toString()).right();
		getContentTable().row();
	}


}
