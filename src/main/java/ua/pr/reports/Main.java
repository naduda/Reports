package ua.pr.reports;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import ua.pr.common.StringCrypter;
import ua.pr.menu.FrameXMLMenuLoader;
import ua.pr.menu.XMLMenuLoader;
import ua.pr.model.ModelDB;
import ua.pr.reports.ui.tools.SetFrameComponent;
import ua.pr.reports.xml.EntityFromXML;
import ua.pr.reports.xml.objects.Base;
import ua.pr.reports.xml.objects.Login;
import ua.pr.ui.login.LoginDialog;

public class Main {

	private static final String CONNECTION_STRING = "jdbc:sqlserver://%s:1433;user=%s;password=%s";
	private static final String CONNECT_XML_PATH = "Settings.xml";
	private static final String MENU_XML_PATH = "Menu.xml";
	private static final String DATE_FORMAT = " dd.MM.yyyy' p. '";
	
	public static JScrollPane reportPanel;
	public static JScrollPane treePanel;
	public static JLabel lbActiveReport;
	public static CreateReport cr;
	public static Object selectedObject;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

//		--------------------------------------------------------------------------
		EntityFromXML efx = new EntityFromXML();
		Base base = (Base)efx.getObject(CONNECT_XML_PATH, Base.class);
		Login login = (Login) base.getLogin();
		login.setStrConn(CONNECTION_STRING);
//		--------------------------------------------------------------------------		
		StringCrypter crypter = new StringCrypter(new byte[]{2,3,0,2,1,9,8,4});
		login.setPassword(crypter.decrypt(login.getPassword()));
//		--------------------------------------------------------------------------
		new LoginDialog(new JFrame(), login);
		if (login.isSave()) {
			login.setPassword(crypter.encrypt(login.getPassword()));
			base.setLogin(login);
			efx.setObject(CONNECT_XML_PATH, base);
		}
//		--------------------------------------------------------------------------
		FrameXMLMenuLoader frm = new FrameXMLMenuLoader(base.getMainForm().getTitle(), MENU_XML_PATH);

		String icoPath = base.getMainForm().getIcoPath();
		if (icoPath != null) {
			ImageIcon img = new ImageIcon(icoPath);
			frm.setIconImage(img.getImage());
		}
		
		XMLMenuLoader loader = frm.getLoader();

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
		ActionListener aList = new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				try {
					cr.createReport();
				} catch (Exception ex) {
					System.err.println(".... " + ex);
				}
			}
		};
		dtBeg.addActionListener(aList);
		dtEnd.addActionListener(aList);
//		-------------------------------------------
		btnExit.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				ModelDB.session.close();
				System.out.println("Session close.");
				System.exit(0);
			}
		});
		
//		--------------------------------------------------------------------------
		final SetFrameComponent frmComponents = new SetFrameComponent();
//		--------------------------------------------------------------------------
		treePanel = new JScrollPane();
		reportPanel = new JScrollPane();
		JSplitPane splitMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, reportPanel);
				
		splitMain.setLeftComponent(treePanel);
//		--------------------------------------------------------------------------
		cr = new CreateReport();
		cr.setDtBeg(dtBeg);
		cr.setDtEnd(dtEnd);
//		--------------------------------------------------------------------------
		List<JMenuItem> reports = new ArrayList<JMenuItem>();
		frmComponents.setReports(menuBar, reports, cr);
		frmComponents.setTree(cr);
//		--------------------------------------------------------------------------
		JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		frmComponents.setStatusBar(statusBar, login);
		
//		--------------------------------------------------------------------------
		frm.add(splitMain, BorderLayout.CENTER);
		frm.add(statusBar, BorderLayout.SOUTH);
		frm.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we) {
		    	ModelDB.session.close();
				System.out.println("Session close.");
		    }
		});
//		frm.setLocationRelativeTo(null);
		cr.createReport();
		frm.pack();
		frm.setVisible(true);	
	}
}
