package pl.mmorpg.prototype.server.database.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name="CharactersQuestsItemReward")
@Table(name="characters_quests_item_reward")
public class CharactersQuestsItemReward extends ItemReward
{
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="quest_id", referencedColumnName="quest_id", nullable = false),
        @JoinColumn(name="character_id", referencedColumnName="character_id", nullable = false)
        })
    private CharactersQuests charactersQuests;
    
    public CharactersQuestsItemReward(ItemIdentifiers itemIdentifier, Integer numberOfItems, CharactersQuests charactersQuests)
    {
        super(itemIdentifier, numberOfItems);
        setCharactersQuests(charactersQuests);
    }
}
