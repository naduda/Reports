package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class TreeObject implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String capt;
	private int idObject;
	private Integer idParent;
	@Column(name="Type")
	private String type;
	private String userId;

    public TreeObject() 
    {
    	
    }

	public int getId() 
	{
		return this.id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getCapt() 
	{
		return this.capt;
	}

	public void setCapt(String capt) 
	{
		this.capt = capt;
	}

	public int getIdObject() 
	{
		return this.idObject;
	}

	public void setIdObject(int idObject) 
	{
		this.idObject = idObject;
	}

	public Integer getIdParent() {
		return this.idParent;
	}

	public void setIdParent(Integer idParent) 
	{
		this.idParent = idParent;
	}

	public String getType() 
	{
		return this.type;
	}

	public void setType(String type) 
	{
		this.type = type;
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
}