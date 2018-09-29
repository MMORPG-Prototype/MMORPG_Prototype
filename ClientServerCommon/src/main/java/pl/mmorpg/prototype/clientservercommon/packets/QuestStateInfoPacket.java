package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class QuestStateInfoPacket
{
    private String questName;
    
    private String description;

    /**
     * See CharactersQuests' entity field
     */
    private String finishedQuestTasksPath;
    
    private QuestTaskInfoPacket rootTask;
}
