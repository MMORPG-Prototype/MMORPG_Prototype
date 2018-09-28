package pl.mmorpg.prototype.server.database.entities.components.keys;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.Character;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"character", "quest"})
public class CharactersQuestsKey implements Serializable
{
    @ManyToOne
    @JoinColumn(name="character_id")
    private Character character;
    
    @ManyToOne
    @JoinColumn(name="quest_id")
    private Quest quest;
}
