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
	@Column(name = "inventory_page_number", nullable = false)
	private Integer inventoryPageNumber;

	@Column(name = "inventory_x", nullable = false)
	private Integer inventoryX;

	@Column(name = "inventory_y", nullable = false)
	private Integer inventoryY;
}
