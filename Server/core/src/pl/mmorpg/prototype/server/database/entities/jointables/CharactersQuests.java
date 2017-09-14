package pl.mmorpg.prototype.server.database.entities.jointables;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.components.keys.CharactersQuestsKey;
import pl.mmorpg.prototype.server.exceptions.QuestMustHaveExaclyOneRootTaskException;
import pl.mmorpg.prototype.server.quests.QuestTask;

@Entity
@Table(name="characters_quests")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of="key")
public class CharactersQuests
{
    @EmbeddedId
    private CharactersQuestsKey key = new CharactersQuestsKey();
    
    @ElementCollection
    @CollectionTable(name="quests_tasks", joinColumns= {
            @JoinColumn(name="character_id", referencedColumnName="character_id"), 
            @JoinColumn(name="quest_id", referencedColumnName="quest_id")
            }, uniqueConstraints=@UniqueConstraint(columnNames= {"character_id", "quest_id", "quest_task"}))
    @Column(name="quest_task")
    @Type(type = "pl.mmorpg.prototype.server.database.jsonconfig.QuestTaskJsonUserType")
    private Collection<QuestTask> questTasks = new ArrayList<>(1);
    
    public CharactersQuests(UserCharacter character, Quest quest)
    {
        setCharacter(character);
        setQuest(quest);
        Collection<QuestTask> nextTasks = quest.getQuestTask().getNextTasks();
        initalizeCurrentQuestTasks(nextTasks);
    }

    private void initalizeCurrentQuestTasks(Collection<QuestTask> nextTasks)
    {
        if(nextTasks.size() != 1)
            throw new QuestMustHaveExaclyOneRootTaskException(nextTasks.size());
        questTasks.add(nextTasks.iterator().next());
    }
    
    @Transient
    public UserCharacter getCharacter()
    {
        return key.getCharacter();
    }
    
    public void setCharacter(UserCharacter character)
    {
        key.setCharacter(character);
    }
    
    @Transient
    public Quest getQuest()
    {
        return key.getQuest();
    }
    
    public void setQuest(Quest quest)
    {
        key.setQuest(quest);
    }
    
}
