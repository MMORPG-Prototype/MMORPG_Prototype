package pl.mmorpg.prototype.client.userinterface.dialogs.components.spell;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.spells.Spell;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.LineBreaker;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.ButtonField;


public class SpellListElement extends Table
{
	public SpellListElement(Spell spell)
	{	
		addSpellIconField(spell);
		addSpellInfo(spell);
	}

	private void addSpellIconField(Spell spell)
	{
		ButtonField<Spell> spellIconField = new ButtonField<>();
		spellIconField.put(spell);
		this.add(spellIconField);
	}

	private void addSpellInfo(Spell spell)
	{
		Table infoContainer = new Table();
		infoContainer.add(new Label(spell.getName(), Settings.DEFAULT_SKIN)).padRight(10);
		infoContainer.add(new LineBreaker(spell.getDescription(), 40));
		infoContainer.padLeft(10);
		this.add(infoContainer);
	}
}
