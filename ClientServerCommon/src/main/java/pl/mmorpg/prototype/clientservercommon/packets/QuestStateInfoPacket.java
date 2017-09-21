package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class QuestStateInfoPacket
{
    private String questName;
    
    private String description;
    
    private QuestTaskInfoPacket[] questTasks;
}
