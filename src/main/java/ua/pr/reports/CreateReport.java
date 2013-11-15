package ua.pr.reports;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import oracle.xdo.template.FOProcessor;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import ua.pr.model.ModelDB;
import ua.pr.reports.common.Parameters;
import ua.pr.reports.common.Template;
import ua.pr.xslt.DataXSLT;
import ua.pr.xslt.ReportXSLT;

public class CreateReport {
	private Template template;
	private JDatePickerImpl dtBeg;
	private JDatePickerImpl dtEnd;
	private String xdocfgPath = "xdo.xml";
	
	public ByteArrayOutputStream res;
	
	public void createReport() {
		create(FOProcessor.FORMAT_HTML, null);
		try {
			JEditorPane editor = new JEditorPane();
			HTMLEditorKit htmlEK = new HTMLEditorKit();
			
			InputStream is = new ByteArrayInputStream(res.toByteArray());
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			editor.setEditorKit(htmlEK);
			Document doc = editor.getDocument();
			
			doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
			htmlEK.read(br, doc, 0);
			int k = doc.getText(0, 100).indexOf("?>") + 2;
			doc.remove(0, k);
			editor.setContentType("text/html; charset=UTF-8");
			editor.setEditable(false);

			Main.reportPanel.setViewportView(editor);
			Main.reportPanel.revalidate();
			Main.reportPanel.repaint();
		} catch (Exception ex) {
			System.err.println("..... " + ex);
		}
	}
	
	public void create(byte fopFormat, String outputFileName) {
		DataXSLT data = new DataXSLT(ModelDB.conn);
		ByteArrayOutputStream baData = null;
		
		Hashtable<String, String> pars = new Hashtable<String, String>();
//		---------------------------------------------------------------------------------
		if (template.getParams().trim().length() > 0) {
			Parameters foParams = new Parameters(dtBeg, dtEnd, Main.selectedObject);
			foParams.setParameters(pars, template.getParams());
		}
//		---------------------------------------------------------------------------------		
		String fileData = "d:/1.xml";
		File ff = new File(fileData);
		if (!ff.exists())
		{
			data.getData(template.getQuery(), template.getDataTemplate(), pars, fileData);
		} 
		baData = data.getData(template.getQuery(), template.getDataTemplate(), pars, null);
		
//		---------------------------------------------------------------------------------
		ReportXSLT rX = new ReportXSLT();
		rX.setXdoConfPath(xdocfgPath);

		String[] strPath = template.getRtfTemplate().split("/");
		String foTemplate = "";
		for (int i = 0; i < strPath.length - 2; i++) {
			foTemplate = foTemplate + strPath[i] + "/";
		}

		foTemplate = foTemplate + "foTemplates/" + strPath[strPath.length - 1].replace("rtf", "xml");
		ff = new File(foTemplate);
		if (!ff.exists())
		{
			rX.RTF2XML(template.getRtfTemplate(), foTemplate);
		}

		res = rX.getReport(baData, foTemplate, fopFormat, outputFileName);
	}
	
	public Template getTemplate() {
		return template;
	}
	
	public void setTemplate(Template template) {
		this.template = template;
	}
	
	public JDatePickerImpl getDtBeg() {
		return dtBeg;
	}
	
	public void setDtBeg(JDatePickerImpl dtBeg) {
		this.dtBeg = dtBeg;
	}
	
	public JDatePickerImpl getDtEnd() {
		return dtEnd;
	}
	
	public void setDtEnd(JDatePickerImpl dtEnd) {
		this.dtEnd = dtEnd;
	}

	public String getXdocfgPath() {
		return xdocfgPath;
	}

	public void setXdocfgPath(String xdocfgPath) {
		this.xdocfgPath = xdocfgPath;
	}
}