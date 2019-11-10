package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Registerable
public class FindPathAndGoToPacket
{
	private int x;
	private int y;
}
