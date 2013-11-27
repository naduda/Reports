package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class PgdiLink implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgdiLinkPK id;
	@Column(name="default")
	private boolean default_;
	@Column(name="id")
	private int id_;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUser", referencedColumnName="IdUser", insertable=false, updatable=false)
	private UserSetting userSetting;

	public PgdiLink() {
	}

	public PgdiLinkPK getId() {
		return this.id;
	}

	public void setId(PgdiLinkPK id) {
		this.id = id;
	}

	public boolean getDefault_() {
		return this.default_;
	}

	public void setDefault_(boolean default_) {
		this.default_ = default_;
	}

	public int getId_() {
		return this.id_;
	}

	public void setId_(int id_) {
		this.id_ = id_;
	}

	public UserSetting getUserSetting() {
		return this.userSetting;
	}

	public void setUserSetting(UserSetting userSetting) {
		this.userSetting = userSetting;
	}
}