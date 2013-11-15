package ua.pr.model.orm;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="LstSubstation")
public class Substation implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IdSubstation")
	private Integer idSubstation;
	@Column(name="Description")
	private String description;
	@Column(name="IdConsumer")
	private Integer idConsumer;
	private Short idNode;
	@Column(name="IdObl")
	private Integer idObl;
	@Column(name="IdRES")
	private Integer idRES;
	@Column(name="Name")
	private String name;
	@OneToMany(mappedBy="lstSubstation")
	@OrderBy("idClassV, capt")
	private List<Account> accounts;

	@Override
	public String toString() {
		return name;
	}

	public int getIdSubstation() 
	{
		return this.idSubstation;
	}

	public void setIdSubstation(int idSubstation) 
	{
		this.idSubstation = idSubstation;
	}

	public String getDescription() 
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getIdConsumer() 
	{
		return this.idConsumer;
	}

	public void setIdConsumer(int idConsumer) 
	{
		this.idConsumer = idConsumer;
	}

	public short getIdNode() 
	{
		return this.idNode;
	}

	public void setIdNode(short idNode) 
	{
		this.idNode = idNode;
	}

	public int getIdObl() 
	{
		return this.idObl;
	}

	public void setIdObl(int idObl)
	{
		this.idObl = idObl;
	}

	public int getIdRES() 
	{
		return this.idRES;
	}

	public void setIdRES(int idRES)
	{
		this.idRES = idRES;
	}

	public String getName() 
	{
		return this.name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public List<Account> getAccounts() 
	{
		return this.accounts;
	}

	public void setAccounts(List<Account> accounts) 
	{
		this.accounts = accounts;
	}	
}