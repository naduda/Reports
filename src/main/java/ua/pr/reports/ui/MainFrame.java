package ua.pr.reports.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import ua.pr.common.ToolsPrLib;
import ua.pr.menu.FrameXMLMenuLoader;
import ua.pr.menu.XMLMenuLoader;
import ua.pr.model.ModelDB;
import ua.pr.reports.common.CreateReport;
import ua.pr.reports.ui.tools.SetFrameComponent;
import ua.pr.reports.xml.objects.Base;

public class MainFrame extends FrameXMLMenuLoader implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String MENU_XML_PATH = "Menu.xml";
	private static final String DATE_FORMAT = " dd.MM.yyyy' p. '";
	
	private JSplitPane splitMain;
	private JScrollPane reportPanel;
	private TreePanel treePanel;
	private JLabel lbActiveReport;
	private CreateReport createReport;
	private Object selectedObject;
	
	private XMLMenuLoader loader;
	private ModelDB mdb;
	private Base base;
	
	public MainFrame(Base base, ModelDB mdb) {		
		super(base.getMainForm().getTitle(), MENU_XML_PATH);

		this.mdb = mdb;
		this.base = base;
		
		String icoPath = base.getMainForm().getIcoPath();
		if (icoPath != null) {
			ImageIcon img = new ImageIcon(icoPath + "/MainWindow.png");
			setIconImage(img.getImage());
		}
		
		loader = getLoader();

		JDatePickerImpl dtBeg = (JDatePickerImpl) loader.getMenuItem("dtBeg");
		JDatePickerImpl dtEnd = (JDatePickerImpl) loader.getMenuItem("dtEnd");
		
		JMenuBar menuBar = loader.getMenuBar("mainMenu");
		JButton btnExit = (JButton) loader.getMenuItem("btnExit");
		lbActiveReport = (JLabel) loader.getMenuItem("lbActiveReport");
		
		dtBeg.setDateFormate(DATE_FORMAT);
		dtEnd.setDateFormate(DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		dtEnd.setDate(calendar);
		calendar.add(Calendar.DATE, -1);
		dtBeg.setDate(calendar);
//		-------------------------------------------
		dtBeg.addActionListener(new ActionListenerDate());
		dtEnd.addActionListener(new ActionListenerDate());
		System.out.println(dtBeg.getListeners(ActionListener.class).length);
//		-------------------------------------------
		btnExit.addActionListener(new WindowListenerMainFRM());	
//		--------------------------------------------------------------------------
		final SetFrameComponent frmComponents = new SetFrameComponent(this);
//		--------------------------------------------------------------------------
		createReport = getCreateReport();
		createReport.setDtBeg(dtBeg);
		createReport.setDtEnd(dtEnd);
//		--------------------------------------------------------------------------
		reportPanel = new JScrollPane();
		splitMain = new JSplitPane();
		splitMain.setRightComponent(reportPanel);
		JScrollPane treePanelScroll = new JScrollPane();
		treePanelScroll.setPreferredSize(new Dimension(400,100)); //Set minimum left size 400
		splitMain.setLeftComponent(treePanelScroll);
//		--------------------------------------------------------------------------		
		frmComponents.setReports(menuBar, createReport);
//		--------------------------------------------------------------------------
		JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		frmComponents.setStatusBar(statusBar, base.getLogin());		
//		--------------------------------------------------------------------------
		add(splitMain, BorderLayout.CENTER);
		add(statusBar, BorderLayout.SOUTH);
		createReport.createReport(this);
		addWindowListener(new WindowListenerMainFRM());
		pack();
	}
	
//  Getters and Setters -------------------------------------------------------------------
	public JScrollPane getReportPanel() {
		return reportPanel;
	}
	
	public void setReportPanel(JScrollPane reportPanel) {
		this.reportPanel = reportPanel;
	}
	
	public TreePanel getTreePanel() {
		return treePanel;
	}
	
	public void setTreePanel(TreePanel treePanel) {
		this.treePanel = treePanel;
		treePanel.setCreateReport(createReport);
		splitMain.setLeftComponent(treePanel);
	}
	
	public JLabel getLbActiveReport() {
		return lbActiveReport;
	}
	
	public void setLbActiveReport(JLabel lbActiveReport) {
		this.lbActiveReport = lbActiveReport;
	}
	
	public CreateReport getCreateReport() {
		if(createReport == null) {
			createReport = new CreateReport();
		}
		return createReport;
	}
	
	public void setCreateReport(CreateReport createReport) {
		this.createReport = createReport;
	}
	
	public Object getSelectedObject() {
		return selectedObject;
	}
	
	public void setSelectedObject(Object selectedObject) {
		this.selectedObject = selectedObject;
	}
	
	public ModelDB getMdb() {
		return mdb;
	}

	public Base getBase() {
		return base;
	}

	//	---------------------------------------------------------------------------------------	
	class ActionListenerDate implements ActionListener, Serializable {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			try {
				createReport.createReport(MainFrame.this);
			} catch (Exception ex) {
				System.err.println(".... " + ex);
			}
		}		
	}
//	---------------------------------------------------------------------------------------
	class WindowListenerMainFRM implements WindowListener, ActionListener, Serializable {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent arg0) {
			MainFrame.this.setVisible(false);
			mdb.getSession().close();
			System.out.println("Session close.");
			System.exit(0);
		}	
		
		public void windowClosing(WindowEvent e) {
			ObjectOutputStream oos = null;
			try {
				String pathOfFormState = ToolsPrLib.getFullPath(base.getMainForm().getPathOfFormState());
				oos = new ObjectOutputStream(new FileOutputStream(new File(pathOfFormState)));
				oos.writeObject(e.getSource());
				System.out.println("end");
			} catch (Exception e2) {
				e2.printStackTrace();
			} finally {
				try {
//					ModelDB.session.close();
					System.out.println("Session close.");
					oos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
