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

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mmorpg.prototype.server.objects.monsters.spells.SpellTypes;

@Entity(name = "UserCharacterSpell")
@Table(name = "user_character_spells")
@Data
@EqualsAndHashCode(of="id")
public class UserCharacterSpell
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "spellType", nullable = false)
    @Enumerated(EnumType.STRING)
    private SpellTypes spellType;
    
    @ManyToOne
    @JoinColumn(name = "user_character_id", nullable = false)
    private UserCharacter userCharacter; 
}
