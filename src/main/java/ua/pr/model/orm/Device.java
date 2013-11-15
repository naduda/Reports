package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="Devices")
public class Device implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private DevicePK id;
	private String description;
	private int idTypeDevice;
	private Connection connection;
	private Soket soket;

    public Device() 
    {
    }

	@EmbeddedId
	public DevicePK getId() 
	{
		return this.id;
	}

	public void setId(DevicePK id) 
	{
		this.id = id;
	}	

	@Column(name="Description")
	public String getDescription() 
	{
		return this.description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	@Column(name="IdTypeDevice")
	public int getIdTypeDevice() 
	{
		return this.idTypeDevice;
	}

	public void setIdTypeDevice(int idTypeDevice) 
	{
		this.idTypeDevice = idTypeDevice;
	}

	@OneToOne(mappedBy="device")
	public Connection getConnection() 
	{
		return this.connection;
	}

	public void setConnection(Connection connection) 
	{
		this.connection = connection;
	}

	@OneToOne(mappedBy="device", fetch=FetchType.LAZY)
	public Soket getSoket() 
	{
		return this.soket;
	}

	public void setSoket(Soket soket) 
	{
		this.soket = soket;
	}	
}