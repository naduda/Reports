package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="LinkAccount")
public class LinkAccount implements Serializable 
{
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	@Column(name="idAccount")
	private Integer idAccount;
	@Column(name="idObject1")
	private Integer idObject1;
	@Column(name="idObject2")
	private Integer idObject2;
	@Column(name="TypeObject")
	private String typeObject;
	@Column(name="Priority")
	private short priority;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idAccount", insertable=false, updatable=false)
	private Account account;
	@OneToMany(mappedBy="linkAccount")
	private List<Meter> meters;
	
	public int getId() 
	{
		return this.id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public Integer getIdAccount()
	{
		return idAccount;
	}

	public void setIdAccount(Integer idAccount)
	{
		this.idAccount = idAccount;
	}

	public Integer getIdObject1()
	{
		return idObject1;
	}

	public void setIdObject1(Integer idObject1)
	{
		this.idObject1 = idObject1;
	}

	public Integer getIdObject2()
	{
		return idObject2;
	}

	public String getTypeObject()
	{
		return typeObject;
	}

	public void setTypeObject(String typeObject)
	{
		this.typeObject = typeObject;
	}

	public short getPriority()
	{
		return priority;
	}

	public void setPriority(short priority)
	{
		this.priority = priority;
	}

	public void setIdObject2(Integer idObject2)
	{
		this.idObject2 = idObject2;
	}

	public Account getAccount() 
	{
		return this.account;
	}

	public void setAccount(Account account) 
	{
		this.account = account;
	}
	
	public List<Meter> getMeters()
	{
		return this.meters;
	}

	public void setMeters(List<Meter> meters) 
	{
		this.meters = meters;
	}	
}