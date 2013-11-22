package ua.pr.reports.xml.objects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

@SuppressWarnings("restriction")
@XmlRootElement(name="Base")
@XmlAccessorType(XmlAccessType.FIELD)
public class Base implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="Login")
	private Login login;
	@XmlElement(name="mainForm")
	private MainFRM mainForm;
	@XmlElement(name="repSettings")
	private ReportsSettings reportsSettings;
	
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login log) {
		this.login = log;
	}

	public MainFRM getMainForm() {
		return mainForm;
	}

	public void setMainForm(MainFRM mainForm) {
		this.mainForm = mainForm;
	}

	public ReportsSettings getReportsSettings() {
		return reportsSettings;
	}

	public void setReportsSettings(ReportsSettings reportsSettings) {
		this.reportsSettings = reportsSettings;
	}
}
