package ua.pr.model.orm;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="UserSettings")
public class UserSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idUser")
	private int idUser;
	@Column(name="FontSize")
	private short fontSize;
	@Column(name="Functions")
	private short functions;
	@Column(name="IdTimeSetZone")
	private short idTimeSetZone;
	@Column(name="Length")
	private short length;
	@Column(name="NameUser")
	private String nameUser;
	@Column(name="Permissions")
	private int permissions;
	@Column(name="StartHour")
	private int startHour;
	@Column(name="UserId")
	private String userId;
	@OneToMany(mappedBy="userSetting")
	private List<PgdiLink> pgdiLinks;
	@ManyToMany
	@JoinTable(name="PgdiLink", joinColumns={@JoinColumn(name="idUser")}
							  , inverseJoinColumns={@JoinColumn(name="idObject")})
	private List<PgdiFileStorage> pgdiFileStorages;

	public UserSetting() {
	}

	public short getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(short fontSize) {
		this.fontSize = fontSize;
	}

	public short getFunctions() {
		return this.functions;
	}

	public void setFunctions(short functions) {
		this.functions = functions;
	}

	public short getIdTimeSetZone() {
		return this.idTimeSetZone;
	}

	public void setIdTimeSetZone(short idTimeSetZone) {
		this.idTimeSetZone = idTimeSetZone;
	}

	public short getLength() {
		return this.length;
	}

	public void setLength(short length) {
		this.length = length;
	}

	public String getNameUser() {
		return this.nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public int getPermissions() {
		return this.permissions;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}

	public int getStartHour() {
		return this.startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<PgdiLink> getPgdiLinks() {
		return this.pgdiLinks;
	}

	public void setPgdiLinks(List<PgdiLink> pgdiLinks) {
		this.pgdiLinks = pgdiLinks;
	}

	public PgdiLink addPgdiLink(PgdiLink pgdiLink) {
		getPgdiLinks().add(pgdiLink);
		pgdiLink.setUserSetting(this);

		return pgdiLink;
	}

	public PgdiLink removePgdiLink(PgdiLink pgdiLink) {
		getPgdiLinks().remove(pgdiLink);
		pgdiLink.setUserSetting(null);

		return pgdiLink;
	}

	public List<PgdiFileStorage> getPgdiFileStorages() {
		return this.pgdiFileStorages;
	}

	public void setPgdiFileStorages(List<PgdiFileStorage> pgdiFileStorages) {
		this.pgdiFileStorages = pgdiFileStorages;
	}
}