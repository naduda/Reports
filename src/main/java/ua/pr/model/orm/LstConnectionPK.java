package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class LstConnectionPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private int idConnect;
	private short idNode;

    public LstConnectionPK() 
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

	public boolean equals(Object other) 
	{
		if (this == other) 
		{
			return true;
		}
		if (!(other instanceof LstConnectionPK)) 
		{
			return false;
		}
		LstConnectionPK castOther = (LstConnectionPK)other;
		return 
			(this.idConnect == castOther.idConnect)
			&& (this.idNode == castOther.idNode);

    }
    
	public int hashCode() 
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idConnect;
		hash = hash * prime + ((int) this.idNode);
		
		return hash;
    }
}