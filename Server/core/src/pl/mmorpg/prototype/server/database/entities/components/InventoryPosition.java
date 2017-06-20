package pl.mmorpg.prototype.server.database.entities.components;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class InventoryPosition
{
	@Column(name = "inventoryPageNumber", nullable = false)
	private Integer inventoryPageNumber;

	@Column(name = "inventoryX", nullable = false)
	private Integer inventoryX;

	@Column(name = "inventoryY", nullable = false)
	private Integer inventoryY;
	
	
}
