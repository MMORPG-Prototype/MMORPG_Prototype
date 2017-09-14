package pl.mmorpg.prototype.server.database.entities.components.keys;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharactersQuestsKey implements Serializable
{
    @ManyToOne
    @JoinColumn(name="character_id")
    private UserCharacter character;
    
    @ManyToOne
    @JoinColumn(name="quest_id")
    private Quest quest;
}
