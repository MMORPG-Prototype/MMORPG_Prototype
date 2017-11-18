package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Data
@Registerable
public class KnownSpellInfoPacket
{
	private SpellIdentifiers spellIdentifer;
}
