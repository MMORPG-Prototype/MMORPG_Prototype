package pl.mmorpg.prototype.server.database.entities;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;

@Data
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(of = "id")
public abstract class ItemReward
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_identifier", nullable = false)
    private ItemIdentifiers itemIdentifier;

    @Column(name = "number_of_items", nullable = false)
    private Integer numberOfItems = 1;

    public ItemReward(ItemIdentifiers itemIdentifier, Integer numberOfItems)
    {
        this.itemIdentifier = itemIdentifier;
        this.numberOfItems = numberOfItems;
    }
}
