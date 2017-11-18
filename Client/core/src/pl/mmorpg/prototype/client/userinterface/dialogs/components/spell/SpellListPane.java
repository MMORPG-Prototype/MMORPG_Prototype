package pl.mmorpg.prototype.client.userinterface.dialogs.components.spell;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import pl.mmorpg.prototype.client.spells.Spell;

public class SpellListPane extends ScrollPane
{
	private final SpellList spellList;

	public SpellListPane()
	{
		super(new SpellList());
		spellList = (SpellList) this.getWidget();
	}
	
	public void addSpell(Spell spell)
	{
		spellList.addSpell(spell);
	}

}
