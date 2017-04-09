package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class ChatMessageReplyPacket
{
	public String message;
	public String nickname;
	public long sourceCharacterId;
}
