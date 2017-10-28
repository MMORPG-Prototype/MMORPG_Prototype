package pl.mmorpg.prototype.server.database.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;

@Getter
@Setter
@ToString(exclude="quest")
@NoArgsConstructor
@Entity(name = "QuestItemReward")
@Table(name = "quest_item_reward")
public class QuestItemReward extends ItemReward
{
    @ManyToOne
    @JoinColumn(name = "quest_id", referencedColumnName = "id", nullable = false)
    private Quest quest;

    public QuestItemReward(ItemIdentifiers itemIdentifier, Integer numberOfItems, Quest quest)
    {
        super(itemIdentifier, numberOfItems); 
        setQuest(quest);
    }
}
