package ua.pr.reports.xml.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@SuppressWarnings("restriction")
@XmlAccessorType(XmlAccessType.FIELD)
public class MainFRM {
	@XmlAttribute
	private String title;
	@XmlAttribute
	private String icoPath;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getIcoPath() {
		return icoPath;
	}
	
	public void setIcoPath(String icoPath) {
		this.icoPath = icoPath;
	}
}
