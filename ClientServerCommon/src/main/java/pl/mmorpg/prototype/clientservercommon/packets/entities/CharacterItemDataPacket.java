package pl.mmorpg.prototype.clientservercommon.packets.entities;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class CharacterItemDataPacket
{
    private Integer id;
    private String name;
    private String type;
    private Integer characterId;
}
