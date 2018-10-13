package pl.mmorpg.prototype.server.database.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;

@Entity(name = "CharacterSpell")
@Table(name = "character_spells")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@ToString(exclude = "characters")
public class CharacterSpell
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "identifier", nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private SpellIdentifiers identifier;

	@ManyToMany(mappedBy = "spells")
	private Collection<Character> characters;
	
	public CharacterSpell(SpellIdentifiers identifier)
	{
		setIdentifier(identifier);
	}
}
