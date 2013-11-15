package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="LstConnections")
public class LstConnection implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private LstConnectionPK id;
	private String capt;
	private String desc;
	private short idType;
	private String ras;
	private List<Meter> meters;
	private List<Connection> connections;

    public LstConnection() 
    {
    }

	@EmbeddedId
	public LstConnectionPK getId() 
	{
		return this.id;
	}

	public void setId(LstConnectionPK id) 
	{
		this.id = id;
	}
	
	@Column(name="Capt")
	public String getCapt() 
	{
		return this.capt;
	}

	public void setCapt(String capt) 
	{
		this.capt = capt;
	}

	@Column(name="[Desc]")
	public String getDesc() 
	{
		return this.desc;
	}

	public void setDesc(String desc) 
	{
		this.desc = desc;
	}

	@Column(name="IdType")
	public short getIdType() 
	{
		return this.idType;
	}

	public void setIdType(short idType) 
	{
		this.idType = idType;
	}

	@Column(name="Ras")
	public String getRas() 
	{
		return this.ras;
	}

	public void setRas(String ras) 
	{
		this.ras = ras;
	}

	@OneToMany(mappedBy="lstConnection")
	public List<Meter> getMeters() 
	{
		return this.meters;
	}

	public void setMeters(List<Meter> meters) 
	{
		this.meters = meters;
	}

	@OneToMany(mappedBy="lstConnection")
	public List<Connection> getConnections() 
	{
		return this.connections;
	}

	public void setConnections(List<Connection> connections) 
	{
		this.connections = connections;
	}	
}