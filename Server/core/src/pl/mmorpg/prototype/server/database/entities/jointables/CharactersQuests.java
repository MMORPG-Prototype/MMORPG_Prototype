package pl.mmorpg.prototype.server.database.entities.jointables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.database.entities.components.keys.CharactersQuestsKey;
import pl.mmorpg.prototype.server.quests.QuestTask;

@Entity(name = "CharactersQuests")
@Table(name = "characters_quests")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "key")
public class CharactersQuests
{
    @EmbeddedId
    private CharactersQuestsKey key = new CharactersQuestsKey();

    @OneToMany(mappedBy = "charactersQuests", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<QuestTaskWrapper> questTasks = new LinkedList<>();

    @OneToMany(mappedBy = "charactersQuests", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<CharactersQuestsItemReward> itemsReward = new ArrayList<>();

    @Column(name = "gold_reward", nullable = false)
    private Integer goldReward;

    public CharactersQuests(Character character, Quest quest)
    {
        setCharacter(character);
        setQuest(quest);
        initializeItemsReward(quest.getItemsReward());
        goldReward = quest.getGoldReward();
        Collection<QuestTask> nextTasks = quest.getQuestTask().getNextTasks();
        initializeCurrentQuestTasks(nextTasks);
    }

    private void initializeItemsReward(Collection<QuestItemReward> itemsReward)
    {
        Collection<CharactersQuestsItemReward> convertedItemsReward = itemsReward.stream()
                .map(item -> new CharactersQuestsItemReward(item.getItemIdentifier(), item.getNumberOfItems(), this))
                .collect(Collectors.toList());
        this.itemsReward.addAll(convertedItemsReward);
    }

    private void initializeCurrentQuestTasks(Collection<QuestTask> nextTasks)
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
    public Character getCharacter()
    {
        return key.getCharacter();
    }

    public void setCharacter(Character character)
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
