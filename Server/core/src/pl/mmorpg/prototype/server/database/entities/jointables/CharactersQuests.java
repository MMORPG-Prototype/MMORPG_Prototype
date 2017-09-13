package pl.mmorpg.prototype.server.database.entities.jointables;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.components.keys.CharactersQuestsKey;

@Entity
@Table(name="characters_quests")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of="key")
public class CharactersQuests
{
    @EmbeddedId
    private CharactersQuestsKey key = new CharactersQuestsKey();
    
    public CharactersQuests(UserCharacter character, Quest quest)
    {
        setCharacter(character);
        setQuest(quest);
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
