package pl.mmorpg.prototype.server.database.entities;

import lombok.Data;
import org.hibernate.annotations.Type;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.quests.QuestTask;

import javax.persistence.*;

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

    @PostLoad
    public void init()
    {
        questTask.setSourceTask(charactersQuests);
    }

    public QuestTask getQuestTask()
    {
        return questTask;
    }

}
