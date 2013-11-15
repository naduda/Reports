package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Element implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IdElement")
	private int idElement;

	@Column(name="Act")
	private int act;

	@Column(name="Capt")
	private String capt;

	@Column(name="Description")
	private String description;

	@Column(name="Direction")
	private short direction;

	@Column(name="IdObject")
	private int idObject;

	@Column(name="IdParameter")
	private int idParameter;

	@Column(name="IdTypeElement")
	private int idTypeElement;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IdGroup")
	private CalcGroup calcGroup;

    public Element() {
    }

	public int getIdElement() {
		return this.idElement;
	}

	public void setIdElement(int idElement) {
		this.idElement = idElement;
	}

	public int getAct() {
		return this.act;
	}

	public void setAct(int act) {
		this.act = act;
	}

	public String getCapt() {
		return this.capt;
	}

	public void setCapt(String capt) {
		this.capt = capt;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getDirection() {
		return this.direction;
	}

	public void setDirection(short direction) {
		this.direction = direction;
	}

	public int getIdObject() {
		return this.idObject;
	}

	public void setIdObject(int idObject) {
		this.idObject = idObject;
	}

	public int getIdParameter() {
		return this.idParameter;
	}

	public void setIdParameter(int idParameter) {
		this.idParameter = idParameter;
	}

	public int getIdTypeElement() {
		return this.idTypeElement;
	}

	public void setIdTypeElement(int idTypeElement) {
		this.idTypeElement = idTypeElement;
	}

	public CalcGroup getCalcGroup() {
		return this.calcGroup;
	}

	public void setCalcGroup(CalcGroup calcGroup) {
		this.calcGroup = calcGroup;
	}

	@Override
	public String toString()
	{
		return capt;
	}
	
}