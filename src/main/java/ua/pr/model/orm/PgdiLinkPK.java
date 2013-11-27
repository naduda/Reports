package ua.pr.model.orm;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class PgdiLinkPK implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idObject;
	private int idProifile;
	private int idUser;

	public PgdiLinkPK() {
	}
	public int getIdObject() {
		return this.idObject;
	}
	public void setIdObject(int idObject) {
		this.idObject = idObject;
	}
	public int getIdProifile() {
		return this.idProifile;
	}
	public void setIdProifile(int idProifile) {
		this.idProifile = idProifile;
	}
	public int getIdUser() {
		return this.idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PgdiLinkPK)) {
			return false;
		}
		PgdiLinkPK castOther = (PgdiLinkPK)other;
		return 
			(this.idObject == castOther.idObject)
			&& (this.idProifile == castOther.idProifile)
			&& (this.idUser == castOther.idUser);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idObject;
		hash = hash * prime + this.idProifile;
		hash = hash * prime + this.idUser;
		
		return hash;
	}
}