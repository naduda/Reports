package ua.pr.model.orm;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.math.BigDecimal;

@Entity
@Table(name="Accounts")
public class Account implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IdAccount")
	private Integer idAccount;

	@Column(name="Capt")
	private String capt;

	@Column(name="Delta")
	private BigDecimal delta;

	@Column(name="Description")
	private String description;

	@Column(name="IdConsumer")
	private Integer idConsumer;

	@Column(name="IdRES")
	private Integer idRES;

	@Column(name="Reserve")
	private Integer reserve;

	private Timestamp TMend;

	private Timestamp TMset;

	private Integer TRcurrent;

	private Integer TRvoltage;

	@Column(name="TypeAccount")
	private String typeAccount;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IdSubstation")
	private Substation lstSubstation;

	@OneToMany(mappedBy="account")
	private List<LinkAccount> linkAccounts;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IdClassV")
	private ClassV classV;

	@Override
	public String toString() {
		return capt;
	}
	
	public int getIdAccount() 
	{
		return this.idAccount;
	}

	public void setIdAccount(int idAccount) 
	{
		this.idAccount = idAccount;
	}

	public String getCapt() 
	{
		return this.capt;
	}

	public void setCapt(String capt)
	{
		this.capt = capt;
	}

	public BigDecimal getDelta()
	{
		return this.delta;
	}

	public void setDelta(BigDecimal delta) 
	{
		this.delta = delta;
	}

	public String getDescription() 
	{
		return this.description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public int getIdConsumer() 
	{
		return this.idConsumer;
	}

	public void setIdConsumer(int idConsumer) 
	{
		this.idConsumer = idConsumer;
	}

	public int getIdRES() 
	{
		if (this.idRES == null) this.idRES = 0;
		return this.idRES;
	}

	public void setIdRES(int idRES)
	{
		this.idRES = idRES;
	}

	public int getReserve() 
	{
		return this.reserve;
	}

	public void setReserve(int reserve) 
	{
		this.reserve = reserve;
	}

	public Timestamp getTMend() 
	{
		return this.TMend;
	}

	public void setTMend(Timestamp TMend) 
	{
		this.TMend = TMend;
	}

	public Timestamp getTMset() 
	{
		return this.TMset;
	}

	public void setTMset(Timestamp TMset) 
	{
		this.TMset = TMset;
	}

	public int getTRcurrent() 
	{
		return this.TRcurrent;
	}

	public void setTRcurrent(int TRcurrent) 
	{
		this.TRcurrent = TRcurrent;
	}

	public int getTRvoltage() 
	{
		return this.TRvoltage;
	}

	public void setTRvoltage(int TRvoltage) 
	{
		this.TRvoltage = TRvoltage;
	}

	public String getTypeAccount() 
	{
		return this.typeAccount;
	}

	public void setTypeAccount(String typeAccount) 
	{
		this.typeAccount = typeAccount;
	}

	public Substation getLstSubstation() 
	{
		return this.lstSubstation;
	}

	public void setLstSubstation(Substation lstSubstation) 
	{
		this.lstSubstation = lstSubstation;
	}
	
	public List<LinkAccount> getLinkAccounts() 
	{
		return this.linkAccounts;
	}

	public void setLinkAccounts(List<LinkAccount> linkAccounts)
	{
		this.linkAccounts = linkAccounts;
	}

	public ClassV getClassV()
	{
		return classV;
	}

	public void setClassV(ClassV classV)
	{
		this.classV = classV;
	}
}