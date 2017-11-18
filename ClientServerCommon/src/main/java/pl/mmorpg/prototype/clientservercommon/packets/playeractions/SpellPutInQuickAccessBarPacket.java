package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Data
@Registerable
public class SpellPutInQuickAccessBarPacket
{
	private int cellPosition;
	private SpellIdentifiers spellIdentifier;
}
