package pl.mmorpg.prototype.server.database.entities.jointables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.mmorpg.prototype.server.database.entities.CharactersQuestsItemReward;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.QuestItemReward;
import pl.mmorpg.prototype.server.database.entities.QuestTaskWrapper;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.components.keys.CharactersQuestsKey;
import pl.mmorpg.prototype.server.quests.QuestTask;

@Entity
@Table(name = "characters_quests")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "key")
public class CharactersQuests
{
    @EmbeddedId
    private CharactersQuestsKey key = new CharactersQuestsKey();

    @OneToMany(mappedBy = "charactersQuests", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<QuestTaskWrapper> questTasks = new LinkedList<QuestTaskWrapper>();

    @OneToMany(mappedBy = "charactersQuests", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<CharactersQuestsItemReward> itemsReward = new ArrayList<>();

    public CharactersQuests(UserCharacter character, Quest quest)
    {
        setCharacter(character);
        setQuest(quest);
        initializeItemsReward(quest.getItemsReward());
        Collection<QuestTask> nextTasks = quest.getQuestTask().getNextTasks();
        initalizeCurrentQuestTasks(nextTasks);
    }

    private void initializeItemsReward(Collection<QuestItemReward> itemsReward)
    {
        Collection<CharactersQuestsItemReward> convertedItemsReward = itemsReward.stream()
            .map(item -> new CharactersQuestsItemReward(item.getItemIdentifier(), item.getNumberOfItems(), this))
            .collect(Collectors.toList());
        this.itemsReward.addAll(convertedItemsReward);
    }

    private void initalizeCurrentQuestTasks(Collection<QuestTask> nextTasks)
    {
        nextTasks.forEach(task ->
        {
            QuestTaskWrapper questTaskWrapper = new QuestTaskWrapper();
            questTaskWrapper.setCharactersQuests(this);
            questTaskWrapper.setQuestTask(task);
            this.questTasks.add(questTaskWrapper);
        });
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
