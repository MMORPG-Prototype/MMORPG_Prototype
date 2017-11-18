package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.objects.icons.spells.Spell;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.ButtonField;

public class SpellQuickAccessDialog extends QuickAccessDialog<Spell>
{
	private UserInterface linkedInterface;

	public SpellQuickAccessDialog(UserInterface linkedInterface)
	{
		super("Quick access");
		this.linkedInterface = linkedInterface;
		this.setX(430);
		this.setY(Gdx.graphics.getHeight() - this.getHeight());
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

}
