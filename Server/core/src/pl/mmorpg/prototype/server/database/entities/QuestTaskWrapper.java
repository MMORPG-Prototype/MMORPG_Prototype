package pl.mmorpg.prototype.server.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.quests.QuestTask;

@Entity(name = "QuestTask")
@Table(name = "quest_tasks")
@Data
public class QuestTaskWrapper
{   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Type(type = "pl.mmorpg.prototype.server.database.jsonconfig.QuestTaskJsonUserType")
    @Column(name = "quest_task", nullable = true, length = 10000)
    private QuestTask questTask;

    @ManyToOne
    @JoinColumns({ 
            @JoinColumn(name = "character_id", referencedColumnName = "character_id"),
            @JoinColumn(name = "quest_id", referencedColumnName = "quest_id") 
            })
    private CharactersQuests charactersQuests;
    
    public QuestTask getQuestTask()
    {
        questTask.setSourceTask(charactersQuests);
        return questTask;
    }
}
