package pl.mmorpg.prototype.server.database.entities.components;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemReward
{
    @Enumerated(EnumType.STRING)
    @Column(name="item_identifier", nullable=false)
    private ItemIdentifiers itemIdentifier;
    
    @Column(name="number_of_items", nullable = false)
    private Integer numberOfItems = 1;
}
