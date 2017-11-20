package pl.mmorpg.prototype.server.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "SpellQuickAccessBarConfigurationElement")
@Table(
		name = "character_spell_quick_access_bar_configuration_elements", 
		uniqueConstraints = 
		@UniqueConstraint(columnNames={
				"character_id", 
				"spell_id", 
				"field_position"
				})
)
@Data
@EqualsAndHashCode(of="id")
public class SpellQuickAccessBarConfigurationElement
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "spell_id", nullable = false)
	private CharacterSpell spell;

	@Column(name = "field_position", nullable = false)
	private Integer fieldPosition;

	@ManyToOne
	@JoinColumn(name = "character_id", nullable = false)
	private Character character;
}
