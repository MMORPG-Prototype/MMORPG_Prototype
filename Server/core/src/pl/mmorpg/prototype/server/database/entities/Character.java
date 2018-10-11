package pl.mmorpg.prototype.server.database.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;

@Entity(name = "Character")
@Table(name = "characters")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"user", "quests"})
public class Character implements Serializable
{
	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "hit_points", nullable = false)
	private Integer hitPoints = 100;

	@Column(name = "mana_points")
	private Integer manaPoints = 100;

	@Column(name = "nickname", unique = true, nullable = false)
	private String nickname;

	@Column(name = "level", nullable = false)
	private Integer level = 1;

	@Column(name = "experience", nullable = false)
	private Integer experience = 0;

	@Column(name = "strength", nullable = false)
	private Integer strength = 5;

	@Column(name = "intelligence", nullable = false)
	private Integer intelligence = 5;

	@Column(name = "dexterity", nullable = false)
	private Integer dexterity = 5;

	@Column(name = "level_up_points", nullable = false)
	private Integer levelUpPoints = 0;

	@Column(name = "gold", nullable = false)
	private Integer gold = 100;

	@Column(name = "last_location_x")
	private Integer lastLocationX = 96;

	@Column(name = "last_location_y")
	private Integer lastLocationY = 96;
	
	@Column(name = "last_location_map", nullable = false)
	private String lastLocationMap = "tiled2";

	@OneToMany(mappedBy = "character")
	@MapKey(name = "fieldPosition")
	private Map<Integer, ItemQuickAccessBarConfigurationElement> itemQuickAccessBarConfig;

	@OneToMany(mappedBy = "character")
	@MapKey(name = "fieldPosition")
	private Map<Integer, SpellQuickAccessBarConfigurationElement> spellQuickAccessBarConfig;

	@OneToMany(mappedBy = "key.character")
	private Collection<CharactersQuests> quests;

	@ManyToMany
	@JoinTable(name = "character_spell", 
		joinColumns = @JoinColumn(name = "character_id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "spell_id", nullable = false)
	)
	private Collection<CharacterSpell> spells;

}
