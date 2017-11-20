package pl.mmorpg.prototype.server.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;

@Entity(name = "ItemQuickAccessBarConfigurationElement")
@Table(
		name = "character_item_quick_access_bar_configuration_elements", 
		uniqueConstraints = 
		@UniqueConstraint(columnNames={
				"character_id", 
				"item_identifier", 
				"field_position"
				})
)
@Data
@EqualsAndHashCode(of="id")
public class ItemQuickAccessBarConfigurationElement
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "item_identifier", nullable = false)
	private ItemIdentifiers itemIdentifier;

	@Column(name = "field_position", nullable = false)
	private Integer fieldPosition;

	@ManyToOne
	@JoinColumn(name = "character_id", nullable = false)
	private Character character;
}
