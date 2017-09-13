package pl.mmorpg.prototype.server.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;

@Entity(name = "CharacterItem")
@Table(
		name = "Character_Items", 
		uniqueConstraints=
		@UniqueConstraint(columnNames={
				"character_id", 
				"inventory_page_number", 
				"inventory_x", 
				"inventory_y"
				})
)
@Data
@EqualsAndHashCode(of="id")
public class CharacterItem
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "identifier", nullable = false)
	private ItemIdentifiers identifier;

	//Used for stackable items
	@Column(name = "count", nullable = false)
	private Integer count = 1;
	
	@ManyToOne
	private UserCharacter character;
	
	private InventoryPosition inventoryPosition;
	
}
