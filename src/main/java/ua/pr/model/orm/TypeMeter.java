package ua.pr.model.orm;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TypeMeters")
public class TypeMeter
{
	@Id
	@Column(name="idTypeMeter")
	private int idTypeMeter;
//	@Column(name="CommFlag")
//	private Integer commFlag;
	@Column(name="Capt")
	private String capt;
	@OneToMany(mappedBy="typeMeter")
	private List<Meter> meter;
	
	public TypeMeter()
	{
		
	}

	public int getIdTypeMeter()
	{
		return idTypeMeter;
	}

	public void setIdTypeMeter(int idTypeMeter)
	{
		this.idTypeMeter = idTypeMeter;
	}

//	public Integer getCommFlag()
//	{
//		return commFlag;
//	}
//
//	public void setCommFlag(Integer commFlag)
//	{
//		this.commFlag = commFlag;
//	}

	public String getCapt()
	{
		return capt;
	}

	public void setCapt(String capt)
	{
		this.capt = capt;
	}

	public List<Meter> getMeter()
	{
		return meter;
	}

	public void setMeter(List<Meter> meter)
	{
		this.meter = meter;
	}
}
