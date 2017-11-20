package pl.mmorpg.prototype.server.database.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;

@Entity(name = "CharacterSpell")
@Table(name = "character_spells")
@Data
@EqualsAndHashCode(of = "id")
public class CharacterSpell
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "identifier", nullable = false)
	@Enumerated(EnumType.STRING)
	private SpellIdentifiers identifier;

	@ManyToMany
    @JoinColumn(table="character_spell", name="character_id", nullable = false)
    private Collection<Character> character;
}
