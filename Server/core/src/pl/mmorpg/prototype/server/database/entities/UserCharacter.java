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

}
