package pl.mmorpg.prototype.client.states.dialogs;

import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class StatisticsDialog extends CustomDialog
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

	@Override
	public CustomDialogs getIdentifier()
	{
		return CustomDialogs.STATISTICS;
	}

}
