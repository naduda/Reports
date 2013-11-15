package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

@Entity 
public class CalcGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IdGroup")
	private int idGroup;

	@Column(name="Act")
	private int act;

	@Column(name="Calculate")
	private short calculate;

	@Column(name="Capt")
	private String capt;

	@Column(name="Description")
	private String description;

	@Column(name="HightParent")
	private int hightParent;

	@Column(name="Owner")
	private String owner;

	@Column(name="TypeGroup")
	private int typeGroup;

	@OneToMany(mappedBy="calcGroup")
	private List<Element> elements;
	
	@Transient
	private List<CalcGroup> groups;
	

    public List<CalcGroup> getGroups()
	{
		return groups;
	}

	public void setGroups(List<CalcGroup> groups)
	{
		this.groups = groups;
	}

	public CalcGroup() {
    }

	public int getIdGroup() {
		return this.idGroup;
	}

	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}

	public int getAct() {
		return this.act;
	}

	public void setAct(int act) {
		this.act = act;
	}

	public short getCalculate() {
		return this.calculate;
	}

	public void setCalculate(short calculate) {
		this.calculate = calculate;
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

	public int getHightParent() {
		return this.hightParent;
	}

	public void setHightParent(int hightParent) {
		this.hightParent = hightParent;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getTypeGroup() {
		return this.typeGroup;
	}

	public void setTypeGroup(int typeGroup) {
		this.typeGroup = typeGroup;
	}

	public List<Element> getElements() {
		return this.elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}	
}