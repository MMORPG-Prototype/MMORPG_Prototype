package pl.mmorpg.prototype.client.userinterface.dialogs.components.spell;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.spells.Spell;

public class SpellList extends Table
{

	public void addSpell(Spell spell)
	{
		SpellListElement spellActor = new SpellListElement(spell);
		this.add(spellActor);
		this.row();
	}

}
