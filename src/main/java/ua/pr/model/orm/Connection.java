package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="Connections")
public class Connection implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private ConnectionPK id;
	private int numOrder;
	private LstConnection lstConnection;
	private Device device;

    public Connection() 
    {
    }

    @Id
	public ConnectionPK getId()
	{
		return id;
	}

	public void setId(ConnectionPK id)
	{
		this.id = id;
	}
	
	@Column(name="NumOrder")
	public int getNumOrder() 
	{
		return this.numOrder;
	}

	public void setNumOrder(int numOrder) 
	{
		this.numOrder = numOrder;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="IdConnect", referencedColumnName="IdConnect", insertable=false, updatable=false),
		@JoinColumn(name="IdNode", referencedColumnName="IdNode", insertable=false, updatable=false)
		})
	public LstConnection getLstConnection() 
	{
		return this.lstConnection;
	}

	public void setLstConnection(LstConnection lstConnection) 
	{
		this.lstConnection = lstConnection;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="Id", referencedColumnName="Id", insertable=false, updatable=false),
		@JoinColumn(name="IdNode", referencedColumnName="IdNode", insertable=false, updatable=false)
		})
	public Device getDevice() 
	{
		return this.device;
	}

	public void setDevice(Device device) 
	{
		this.device = device;
	}	
}