package ua.pr.model.orm;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="Meters")
public class Meter implements Serializable 
{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private MeterPK id;
	@Column(name="Capt")
	private String capt;
	@Column(name="ConnectType")
	private Short connectType;
	@Column(name="DelayHD")
	private Integer delayHD;
	@Column(name="[Desc]")
	private String desc;
	private Short DLTOff;
	private Short DLTOn;
	@Column(name="IdConnect")
	private Integer idConnect;
	@Column(name="MaxValue")
	private Integer maxValue;
	private Short NChannel;
	private String NFactory;
	private String NMeter;
	@Column(name="Password")
	private String password;
	@Column(name="ReadTimeout")
	private Integer readTimeout;
	@Column(name="Repeat")
	private Short repeat;
	private Short reverse;
	@Column(name="State")
	private Short state;
	@Column(name="TimeOffset")
	private String timeOffset;
	@Column(name="TimeOut")
	private Integer timeOut;
	@Column(name="WriteTimeout")
	private Integer writeTimeout;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="idNode", referencedColumnName="idObject1", insertable=false, updatable=false),
		@JoinColumn(name="idMeter", referencedColumnName="idObject2", insertable=false, updatable=false)
		})
	private LinkAccount linkAccount;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IdType")
	private TypeMeter typeMeter;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="IdConnect", insertable=false, updatable=false),
		@JoinColumn(name="IdNode", insertable=false, updatable=false)
		})
	private LstConnection lstConnection;

	@Override
	public String toString() {
		return capt;
	}

	public MeterPK getId()
	{
		return id;
	}

	public void setId(MeterPK id)
	{
		this.id = id;
	}

	public String getCapt()
	{
		return capt;
	}

	public void setCapt(String capt)
	{
		this.capt = capt;
	}

	public Short getConnectType()
	{
		return connectType;
	}

	public void setConnectType(Short connectType)
	{
		this.connectType = connectType;
	}

	public Integer getDelayHD()
	{
		return delayHD;
	}

	public void setDelayHD(Integer delayHD)
	{
		this.delayHD = delayHD;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public Short getDLTOff()
	{
		return DLTOff;
	}

	public void setDLTOff(Short dLTOff)
	{
		DLTOff = dLTOff;
	}

	public Short getDLTOn()
	{
		return DLTOn;
	}

	public void setDLTOn(Short dLTOn)
	{
		DLTOn = dLTOn;
	}

	public Integer getIdConnect()
	{
		return idConnect;
	}

	public void setIdConnect(Integer idConnect)
	{
		this.idConnect = idConnect;
	}

	public Integer getMaxValue()
	{
		return maxValue;
	}

	public void setMaxValue(Integer maxValue)
	{
		this.maxValue = maxValue;
	}

	public Short getNChannel()
	{
		return NChannel;
	}

	public void setNChannel(Short nChannel)
	{
		NChannel = nChannel;
	}

	public String getNFactory()
	{
		return NFactory;
	}

	public void setNFactory(String nFactory)
	{
		NFactory = nFactory;
	}

	public String getNMeter()
	{
		return NMeter;
	}

	public void setNMeter(String nMeter)
	{
		NMeter = nMeter;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Integer getReadTimeout()
	{
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout)
	{
		this.readTimeout = readTimeout;
	}

	public Short getRepeat()
	{
		return repeat;
	}

	public void setRepeat(Short repeat)
	{
		this.repeat = repeat;
	}

	public Short getReverse()
	{
		return reverse;
	}

	public void setReverse(Short reverse)
	{
		this.reverse = reverse;
	}

	public Short getState()
	{
		return state;
	}

	public void setState(Short state)
	{
		this.state = state;
	}

	public String getTimeOffset()
	{
		return timeOffset;
	}

	public void setTimeOffset(String timeOffset)
	{
		this.timeOffset = timeOffset;
	}

	public Integer getTimeOut()
	{
		return timeOut;
	}

	public void setTimeOut(Integer timeOut)
	{
		this.timeOut = timeOut;
	}

	public Integer getWriteTimeout()
	{
		return writeTimeout;
	}

	public void setWriteTimeout(Integer writeTimeout)
	{
		this.writeTimeout = writeTimeout;
	}

	public LinkAccount getLinkAccount()
	{
		return linkAccount;
	}

	public void setLinkAccount(LinkAccount linkAccount)
	{
		this.linkAccount = linkAccount;
	}

	public TypeMeter getTypeMeter()
	{
		return typeMeter;
	}

	public void setTypeMeter(TypeMeter typeMeter)
	{
		this.typeMeter = typeMeter;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public LstConnection getLstConnection()
	{
		return lstConnection;
	}

	public void setLstConnection(LstConnection lstConnection)
	{
		this.lstConnection = lstConnection;
	}
}