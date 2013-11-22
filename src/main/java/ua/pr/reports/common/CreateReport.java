package ua.pr.reports.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Hashtable;

import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

import oracle.xdo.template.FOProcessor;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import ua.pr.common.ToolsPrLib;
import ua.pr.reports.ui.MainFrame;
import ua.pr.xslt.DataXSLT;
import ua.pr.xslt.ReportXSLT;

public class CreateReport implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Template template;
	private JDatePickerImpl dtBeg;
	private JDatePickerImpl dtEnd;
	private String xdocfgPath = "xdo.xml";
	private MainFrame mainFrame;
	
	transient private ByteArrayOutputStream res;
	
	public void createReport(MainFrame mainFrame) {
		this.mainFrame = mainFrame;		
		try {
			create(FOProcessor.FORMAT_HTML, null);
			
			JEditorPane editor = new JEditorPane();
			HTMLEditorKit htmlEK = new HTMLEditorKit();
			
			InputStream is = new ByteArrayInputStream(res.toByteArray());
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			editor.setEditorKit(htmlEK);
			Document doc = editor.getDocument();
			
			doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
			htmlEK.read(br, doc, 0);
			int k = 0;
			try {
				k = doc.getText(0, 48).indexOf("?>") + 2;
			} catch (Exception e) {
				System.out.println("doc length = " + doc.getLength() + " --- our lenth = 48");				
			}
			doc.remove(0, k);
			editor.setContentType("text/html; charset=UTF-8");
			editor.setEditable(false);

			mainFrame.getReportPanel().setViewportView(editor);
			mainFrame.getReportPanel().revalidate();
			mainFrame.getReportPanel().repaint();
		} catch (Exception ex) {
			System.err.println("..... " + ex);
			ex.printStackTrace();
		}
	}
	
	public void create(byte fopFormat, String outputFileName) {
		DataXSLT data = new DataXSLT(mainFrame.getMdb().getConn());
		ByteArrayOutputStream baData = null;
		
		Hashtable<String, String> pars = new Hashtable<String, String>();
//		---------------------------------------------------------------------------------
		if ((template.getParams() != null) && (template.getParams().trim().length() > 0)) {
			Parameters foParams = new Parameters(dtBeg, dtEnd, mainFrame.getSelectedObject());
			foParams.setParameters(pars, template.getParams());
		}
//		---------------------------------------------------------------------------------		
		String fileData = ToolsPrLib.getFullPath(mainFrame.getBase().getReportsSettings().getDataXMLpath());
		File ff = new File(fileData);

		if (!ff.exists())
		{
			System.out.println("data...");
			data.getData(template.getQuery(), template.getDataTemplate(), pars, fileData);
		} 
		baData = data.getData(template.getQuery(), template.getDataTemplate(), pars, null);
		
//		---------------------------------------------------------------------------------
		ReportXSLT rX = new ReportXSLT();
		rX.setXdoConfPath(xdocfgPath);

		String[] strPath = template.getRtfTemplate().split("/");

		String rtfFilePath = template.getRtfTemplate();
		File rtfFile = new File(rtfFilePath);
		if (!rtfFile.exists()) {
			try {
				String temp = rtfFilePath.replace(rtfFilePath.substring(rtfFilePath.lastIndexOf("/") + 1), "shTemp.rtf");
				CopyOption[] options = new CopyOption[] {StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES};
				Files.copy(Paths.get(temp), Paths.get(rtfFilePath), options);
			} catch (IOException e) {
				System.out.println("*******************");
				e.printStackTrace();
				System.out.println("*******************");
			}
		}
		
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
	
//	Getters and Setters
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

	public MainFrame getMainFrame() {
		return mainFrame;
	}
}
