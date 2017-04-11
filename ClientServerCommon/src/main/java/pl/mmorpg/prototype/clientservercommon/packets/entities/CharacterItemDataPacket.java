package pl.mmorpg.prototype.clientservercommon.packets.entities;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class CharacterItemDataPacket
{
    private long id;
    private String identifier;
}
