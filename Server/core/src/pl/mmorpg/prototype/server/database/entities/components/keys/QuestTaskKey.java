package pl.mmorpg.prototype.server.database.entities.components.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mmorpg.prototype.server.quests.QuestTask;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestTaskKey implements Serializable
{
    private CharactersQuestsKey key = new CharactersQuestsKey();
    
    @Type(type = "pl.mmorpg.prototype.server.database.jsonconfig.QuestTaskJsonUserType")
    @Column(name="quest_task", nullable = false, length = 10000)
    private QuestTask questTask;
}
