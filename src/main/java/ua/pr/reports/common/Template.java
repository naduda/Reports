package ua.pr.reports.common;

import java.io.Serializable;

public class Template implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String rtfTemplate;
	private String dataTemplate;
	private String query;
	private String params;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRtfTemplate() {
		return rtfTemplate;
	}
	public void setRtfTemplate(String rtfTemplate) {
		this.rtfTemplate = rtfTemplate;
	}
	public String getDataTemplate() {
		return dataTemplate;
	}
	public void setDataTemplate(String dataTemplate) {
		this.dataTemplate = dataTemplate;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
}
