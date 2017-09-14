package pl.mmorpg.prototype.server.database.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Embeddable
public class QuestTask
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
   
    
    @Type(type = "pl.mmorpg.prototype.server.database.jsonconfig.QuestTaskJsonUserType")
    @Column(name="quest_task", nullable = false, length = 10000)
    private QuestTask questTask;
}
