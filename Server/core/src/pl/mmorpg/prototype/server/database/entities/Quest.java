package pl.mmorpg.prototype.server.database.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.quests.QuestTask;

@Entity(name = "Quest")
@Table(name = "Quests")
@Data
@EqualsAndHashCode(of="id")
public class Quest
{ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", nullable = false, unique=true)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;
	

	@Type(type = "pl.mmorpg.prototype.server.database.entities.QuestTaskJsonUserType")
	@Column(name="quest_task")
	private QuestTask questTask;
	
	
//	@ManyToMany
//    @JoinTable(name="user_characters_quests",
//        joinColumns=@JoinColumn(name="quest_id"),
//        inverseJoinColumns=@JoinColumn(name="character_id"))
//	private Collection<UserCharacter> users;
	
	@OneToMany(mappedBy="key.quest")
    //@MapKey(name = "questName")
    private Set<CharactersQuests> characters;
}

