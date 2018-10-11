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
	@Column(name = "inventory_page_number")
	private Integer inventoryPageNumber;

	@Column(name = "inventory_x")
	private Integer inventoryX;

	@Column(name = "inventory_y")
	private Integer inventoryY;
}
