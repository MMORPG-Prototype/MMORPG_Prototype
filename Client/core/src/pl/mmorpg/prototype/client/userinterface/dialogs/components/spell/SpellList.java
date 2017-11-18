package pl.mmorpg.prototype.client.userinterface.dialogs.components.spell;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.objects.icons.spells.Spell;
import pl.mmorpg.prototype.client.userinterface.UserInterface;

public class SpellList extends Table
{
	private UserInterface userInterface;

	public SpellList(UserInterface userInterface)
	{
		this.userInterface = userInterface;
	}
	
	public void addSpell(Spell spell)
	{
		SpellListElement spellActor = new SpellListElement(spell, userInterface);
		this.add(spellActor);
		this.row();
	}

}
