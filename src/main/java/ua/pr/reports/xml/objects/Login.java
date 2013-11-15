package ua.pr.reports.xml.objects;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import ua.pr.ui.login.ILogin;

@SuppressWarnings("restriction")
@XmlAccessorType(XmlAccessType.FIELD)
public class Login implements ILogin{
	@XmlAttribute
	private String server;
	@XmlAttribute
	private String database;
	@XmlAttribute
	private String user;
	@XmlAttribute
	private String password;
	@XmlTransient
	private String strConn;
	@XmlTransient
	private boolean save; 
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getDB() {
		return database;
	}
	public void setDB(String database) {
		this.database = database;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStrConn() {
		return strConn;
	}
	public void setStrConn(String strConn) {
		this.strConn = strConn;
	}
	public Boolean isSave() {
		return save;
	}
	public void setSave(boolean save) {
		this.save = save;
	}
}
