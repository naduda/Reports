package ua.pr.reports.xml.objects;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

@SuppressWarnings("restriction")
@XmlRootElement(name="Base")
@XmlAccessorType(XmlAccessType.FIELD)
public class Base {
	@XmlElement(name="Login")
	private Login login;
	@XmlElement(name="mainForm")
	private MainFRM mainForm;
	
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
}
