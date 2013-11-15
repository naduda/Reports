package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class SoketPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private int idSoketClient;
	private short idNode;
	private int id;

    public SoketPK() 
    {
    }

	@Column(name="IdSoketClient")
	public int getIdSoketClient() 
	{
		return this.idSoketClient;
	}
	public void setIdSoketClient(int idSoketClient) 
	{
		this.idSoketClient = idSoketClient;
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
		if (!(other instanceof SoketPK)) 
		{
			return false;
		}
		SoketPK castOther = (SoketPK)other;
		return 
			(this.idSoketClient == castOther.idSoketClient)
			&& (this.idNode == castOther.idNode)
			&& (this.id == castOther.id);
    }
    
	public int hashCode() 
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idSoketClient;
		hash = hash * prime + ((int) this.idNode);
		hash = hash * prime + this.id;
		
		return hash;
    }
}