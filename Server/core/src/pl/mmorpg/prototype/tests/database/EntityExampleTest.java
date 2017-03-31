package pl.mmorpg.prototype.tests.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EntityExampleTests")
public class EntityExampleTest
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "data")
	private String data;

	public Integer getId()
	{
		return id;
	}

	public String getData()
	{
		return data;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public void setData(String data)
	{
		this.data = data;
	}
}
