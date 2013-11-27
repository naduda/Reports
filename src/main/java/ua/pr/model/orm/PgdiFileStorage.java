package ua.pr.model.orm;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

@Entity
public class PgdiFileStorage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Lob
	private byte[] binary;
	@Column(name="[desc]")
	private String desc;
	private String ext;
	private String path;
	@ManyToMany(mappedBy="pgdiFileStorages")
	private List<UserSetting> userSettings;

	public PgdiFileStorage() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getBinary() {
		return this.binary;
	}

	public void setBinary(byte[] binary) {
		this.binary = binary;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getExt() {
		return this.ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<UserSetting> getUserSettings() {
		return this.userSettings;
	}

	public void setUserSettings(List<UserSetting> userSettings) {
		this.userSettings = userSettings;
	}

}