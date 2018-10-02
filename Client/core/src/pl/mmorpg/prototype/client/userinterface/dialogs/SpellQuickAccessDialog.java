package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.objects.icons.spells.Spell;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.packethandlers.UserInterfacePacketHandlerBase;
import pl.mmorpg.prototype.client.spells.SpellFactory;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.ButtonField;
import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SpellPutInQuickAccessBarPacket;

public class SpellQuickAccessDialog extends QuickAccessDialog<Spell>
{
	private UserInterface linkedInterface;

	public SpellQuickAccessDialog(UserInterface linkedInterface, PacketHandlerRegisterer registerer)
	{
		super("Quick access");
		this.linkedInterface = linkedInterface;
		this.setX(430);
		this.setY(Gdx.graphics.getHeight() - this.getHeight());
		
		registerer.registerPrivateClassPacketHandlers(this);
	}

	@Override
	protected ButtonField<Spell> createField(int cellPosition)
	{
		ButtonField<Spell> inventoryField = new ButtonField<>();
		inventoryField.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				fieldClicked(cellPosition);
			}
		});
		return inventoryField;
	}

	private void fieldClicked(int cellPosition)
	{
		linkedInterface.spellQuickAccessButtonClicked(quickAccessButtons.get(cellPosition), cellPosition);
	}

	public void useButtonSpell(int cellPosition, PacketsSender packetSender)
	{
		Spell spell = quickAccessButtons.get(cellPosition).getContent();
		packetSender.send(spell.makeUsagePacket());
	}
	
	@SuppressWarnings("unused")
	private class SpellPutInQuickAccessBarPacketHandler extends UserInterfacePacketHandlerBase<SpellPutInQuickAccessBarPacket>
	{
		@Override
		protected void doHandle(SpellPutInQuickAccessBarPacket packet)
		{
			putSpell(packet.getSpellIdentifier(), packet.getCellPosition());
		}
		
		private void putSpell(SpellIdentifiers spellIdentifier, int cellPosition)
		{
			Spell spell = SpellFactory.produce(spellIdentifier);
			quickAccessButtons.get(cellPosition).put(spell);	
		}
	}

}
