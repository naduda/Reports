package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class LinkAccountPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private Integer idAccount;
	@Column(name="TypeObject")
	private String typeObject;
	@Column(name="Priority")
	private short priority;

    public LinkAccountPK() 
    {
    }
    
	public Integer getIdAccount() 
	{
		return this.idAccount;
	}
	
	public void setIdAccount(int idAccount) 
	{
		this.idAccount = idAccount;
	}
	
	public String getTypeObject() 
	{
		return this.typeObject;
	}
	
	public void setTypeObject(String typeObject) 
	{
		this.typeObject = typeObject;
	}
	
	public short getPriority() 
	{
		return this.priority;
	}
	
	public void setPriority(short priority) 
	{
		this.priority = priority;
	}

	public boolean equals(Object other) 
	{
		if (this == other)
		{
			return true;
		}
		if (!(other instanceof LinkAccountPK)) 
		{
			return false;
		}
		LinkAccountPK castOther = (LinkAccountPK)other;
		return 
			(this.idAccount == castOther.idAccount)
			&& this.typeObject.equals(castOther.typeObject)
			&& (this.priority == castOther.priority);

    }
    
	public int hashCode() 
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idAccount;
		hash = hash * prime + this.typeObject.hashCode();
		hash = hash * prime + ((int) this.priority);
		
		return hash;
    }
}