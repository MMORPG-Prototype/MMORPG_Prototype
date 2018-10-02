package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.objects.icons.spells.Spell;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.packethandlers.UserInterfacePacketHandlerBase;
import pl.mmorpg.prototype.client.spells.SpellFactory;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.CloseButton;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.spell.SpellListPane;
import pl.mmorpg.prototype.clientservercommon.packets.KnownSpellInfoPacket;

public class SpellListDialog extends Dialog
{
	private SpellListPane spellListPane;

	public SpellListDialog(UserInterface userInterface, PacketHandlerRegisterer packetHandlerRegisterer)
	{
		super("Spells", Settings.DEFAULT_SKIN);
		Button closeButton = new CloseButton(this); 
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();

        spellListPane = new SpellListPane(userInterface);
        this.getContentTable().add(spellListPane).fillX();
        this.getContentTable().row();
        this.setHeight(150);
        this.setWidth(470);
        
        packetHandlerRegisterer.registerPrivateClassPacketHandlers(this);
	}
	
	
	private class KnownSpellInfoPacketHandler extends UserInterfacePacketHandlerBase<KnownSpellInfoPacket>
	{
		@Override
		protected void doHandle(KnownSpellInfoPacket packet)
		{
			Spell spell = SpellFactory.produce(packet.getSpellIdentifer());
			addSpell(spell);
		}
		
		private void addSpell(Spell spell)
		{
			spellListPane.addSpell(spell);
		}
	}

}
