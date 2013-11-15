package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class MeterPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
	@Column(name="IdMeter")
	private Integer idMeter;
	@Column(name="IdNode")
	private Short idNode;
    
	public MeterPK()
    {
    }
	
	public int getIdMeter() 
	{
		return this.idMeter;
	}
	public void setIdMeter(int idMeter) 
	{
		this.idMeter = idMeter;
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
		if (!(other instanceof MeterPK)) 
		{
			return false;
		}
		MeterPK castOther = (MeterPK)other;
		return 
			(this.idMeter == castOther.idMeter)
			&& (this.idNode == castOther.idNode);

    }
    
	public int hashCode() 
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idMeter;
		hash = hash * prime + ((int) this.idNode);
		
		return hash;
    }
}