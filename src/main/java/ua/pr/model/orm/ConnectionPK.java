package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class ConnectionPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private int idConnect;
	private short idNode;
	private int id;

    public ConnectionPK() 
    {
    }

	@Column(name="IdConnect")
	public int getIdConnect() 
	{
		return this.idConnect;
	}
	public void setIdConnect(int idConnect) 
	{
		this.idConnect = idConnect;
	}

	@Column(name="IdNode")
	public short getIdNode() 
	{
		return this.idNode;
	}
	public void setIdNode(short idNode) 
	{
		this.idNode = idNode;
	}

	@Column(name="Id")
	public int getId() 
	{
		return this.id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}

	public boolean equals(Object other) 
	{
		if (this == other) 
		{
			return true;
		}
		if (!(other instanceof ConnectionPK)) 
		{
			return false;
		}
		ConnectionPK castOther = (ConnectionPK)other;
		return 
			(this.idConnect == castOther.idConnect)
			&& (this.idNode == castOther.idNode)
			&& (this.id == castOther.id);
    }
    
	public int hashCode() 
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idConnect;
		hash = hash * prime + ((int) this.idNode);
		hash = hash * prime + this.id;
		
		return hash;
    }
}