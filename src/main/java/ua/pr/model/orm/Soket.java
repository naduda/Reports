package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Soket implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private SoketPK id;
	private String capt;
	private String desc;
	private String ip;
	private String ipLocale;
	private int port;
	private short portNumber;
	private Device device;

    public Soket() 
    {
    }

	@EmbeddedId
	public SoketPK getId() 
	{
		return this.id;
	}

	public void setId(SoketPK id)
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

	@Column(name="Ip")
	public String getIp() 
	{
		return this.ip;
	}

	public void setIp(String ip) 
	{
		this.ip = ip;
	}

	@Column(name="IpLocale")
	public String getIpLocale() 
	{
		return this.ipLocale;
	}

	public void setIpLocale(String ipLocale) 
	{
		this.ipLocale = ipLocale;
	}

	@Column(name="Port")
	public int getPort() 
	{
		return this.port;
	}

	public void setPort(int port) 
	{
		this.port = port;
	}

	@Column(name="PortNumber")
	public short getPortNumber() 
	{
		return this.portNumber;
	}

	public void setPortNumber(short portNumber) 
	{
		this.portNumber = portNumber;
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