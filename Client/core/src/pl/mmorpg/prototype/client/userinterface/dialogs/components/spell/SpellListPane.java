package pl.mmorpg.prototype.client.userinterface.dialogs.components.spell;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import pl.mmorpg.prototype.client.objects.icons.spells.Spell;
import pl.mmorpg.prototype.client.userinterface.UserInterface;

public class SpellListPane extends ScrollPane
{
	private final SpellList spellList;

	public SpellListPane(UserInterface userInterface)
	{
		super(new SpellList(userInterface));
		spellList = (SpellList) this.getActor();
		this.setCullingArea(null);
	}
	
	public void addSpell(Spell spell)
	{
		spellList.addSpell(spell);
	}

}
