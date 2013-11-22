package ua.pr.reports.xml.objects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@SuppressWarnings("restriction")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportsSettings implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String dataXMLpath;

	public String getDataXMLpath() {
		return dataXMLpath;
	}

	public void setDataXMLpath(String dataXMLpath) {
		this.dataXMLpath = dataXMLpath;
	}
}
