package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class CharacterCreationReplyPacket
{
	private boolean isCreated = false;
	private String errorMessage;
}
