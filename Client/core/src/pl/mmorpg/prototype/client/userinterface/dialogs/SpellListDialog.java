package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.objects.icons.spells.Spell;
import pl.mmorpg.prototype.client.spells.FireballSpell;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.spell.SpellListPane;

public class SpellListDialog extends Dialog
{
	private SpellListPane spellListPane;

	public SpellListDialog(UserInterface userInterface)
	{
		super("Spells", Settings.DEFAULT_SKIN);
		Button closeButton = new CloseButton(this); 
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();

        spellListPane = new SpellListPane(userInterface);
        this.getContentTable().add(spellListPane).fillX();
        this.getContentTable().row();
        //TODO remove, it is temp
        spellListPane.addSpell(new FireballSpell());
        this.pack();
	}
	
	public void addSpell(Spell spell)
	{
		spellListPane.addSpell(spell);
	}

}
