package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ClassV")
public class ClassV implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IdClassV")
	private int idClassV;

	@Column(name="Description")
	private String description;

	@OneToMany(mappedBy="classV")
	private List<Account> accounts;

    public ClassV() 
    {
    	
    }

	public int getIdClassV() 
	{
		return this.idClassV;
	}

	public void setIdClassV(int idClassV) 
	{
		this.idClassV = idClassV;
	}

	public String getDescription() 
	{
		return this.description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
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