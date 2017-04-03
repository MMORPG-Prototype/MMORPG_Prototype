package pl.mmorpg.prototype.server.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "UserCharacter")
@Table(name = "User_Characters")
public class UserCharacter implements java.io.Serializable
{
	@ManyToOne
	private User user;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nickname", unique = true, nullable = false)
	private String nickname;

	@Column(name = "level", nullable = false)
	private Integer level = 1;

	@Column(name = "experience", nullable = false)
	private Integer experience = 0;

	@Column(name = "strength", nullable = false)
	private Integer strength = 5;

	@Column(name = "magic", nullable = false)
	private Integer magic = 5;

	@Column(name = "dexitirity", nullable = false)
	private Integer dexitirity = 5;

	public Integer getId()
	{
		return id;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Integer getLevel()
	{
		return level;
	}

	public void setLevel(Integer level)
	{
		this.level = level;
	}

	public Integer getExperience()
	{
		return experience;
	}

	public Integer getStrength()
	{
		return strength;
	}

	public Integer getMagic()
	{
		return magic;
	}

	public Integer getDexitirity()
	{
		return dexitirity;
	}

	public void setExperience(Integer experience)
	{
		this.experience = experience;
	}

	public void setStrength(Integer strength)
	{
		this.strength = strength;
	}

	public void setMagic(Integer magic)
	{
		this.magic = magic;
	}

	public void setDexitirity(Integer dexitirity)
	{
		this.dexitirity = dexitirity;
	}

}
