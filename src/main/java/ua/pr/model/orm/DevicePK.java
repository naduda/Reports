package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class DevicePK implements Serializable 
{
	private static final long serialVersionUID = 1L;
	@Column(name="Id")
	private int id;
	@Column(name="IdNode")
	private short idNode;

    public DevicePK() 
    {
    }
	
	public int getId() 
	{
		return this.id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public short getIdNode() 
	{
		return this.idNode;
	}
	public void setIdNode(short idNode) 
	{
		this.idNode = idNode;
	}

	public boolean equals(Object other) 
	{
		if (this == other) 
		{
			return true;
		}
		if (!(other instanceof DevicePK)) 
		{
			return false;
		}
		DevicePK castOther = (DevicePK)other;
		return 
			(this.id == castOther.id)
			&& (this.idNode == castOther.idNode);
    }
    
	public int hashCode() 
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + ((int) this.idNode);
		
		return hash;
    }
}