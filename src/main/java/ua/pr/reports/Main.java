package ua.pr.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;

import ua.pr.common.StringCrypter;
import ua.pr.common.ToolsPrLib;
import ua.pr.model.ModelDB;
import ua.pr.reports.ui.MainFrame;
import ua.pr.reports.ui.TreePanel;
import ua.pr.reports.xml.EntityFromXML;
import ua.pr.reports.xml.objects.Base;
import ua.pr.reports.xml.objects.Login;
import ua.pr.ui.login.LoginDialog;

public class Main {

	private static final String CONNECTION_STRING = "jdbc:sqlserver://%s:1433;user=%s;password=%s";
	private static final String CONNECT_XML_PATH = "Settings.xml";
	
	public Main() {
//		--------------------------------------------------------------------------
		EntityFromXML efx = new EntityFromXML();
		Base base = (Base)efx.getObject(CONNECT_XML_PATH, Base.class);
		Login login = (Login) base.getLogin();
		login.setStrConn(CONNECTION_STRING);
//		--------------------------------------------------------------------------		
		StringCrypter crypter = new StringCrypter(new byte[]{2,3,0,2,1,9,8,4});
		login.setPassword(crypter.decrypt(login.getPassword()));
		base.setLogin(login);
//		--------------------------------------------------------------------------
		new LoginDialog(new JFrame(), login);
		if (login.isSave()) {
			login.setPassword(crypter.encrypt(login.getPassword()));
			base.setLogin(login);
			efx.setObject(CONNECT_XML_PATH, base);
		}
//		--------------------------------------------------------------------------
		ModelDB mdb = new ModelDB(login);
//		--------------------------------------------------------------------------
		
//		--------------------------------------------------------------------------
		MainFrame mainFrame = null;
		String pathOfFormState = ToolsPrLib.getFullPath(base.getMainForm().getPathOfFormState());
		File frmState = new File(pathOfFormState);
		if (frmState.exists()) {
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(new FileInputStream(frmState));
				mainFrame = ((MainFrame) ois.readObject());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			mainFrame = new MainFrame(base, mdb);
			mainFrame.pack();
		}
//		---------------------------------------------------------------------
		DefaultMutableTreeNode searchNode = null;
		if (mainFrame.getTreePanel() != null) {
			searchNode = mainFrame.getTreePanel().getSelectedNode();
		}
		
		mainFrame.setTreePanel(new TreePanel(mdb, base));	
		
		if (searchNode != null) {
			JTree tree = mainFrame.getTreePanel().getTree();
				        
	        DefaultMutableTreeNode par = (DefaultMutableTreeNode)searchNode.getParent();
	        DefaultMutableTreeNode searchNode2 = searchNode;
	        List<Integer> lsIndex = new ArrayList<Integer>();
	        while (par.getLevel() > 0) {
	        	lsIndex.add(par.getIndex(searchNode2));
				searchNode2 = par;
				par = (DefaultMutableTreeNode)par.getParent();
			}
	        lsIndex.add(par.getIndex(searchNode2));
	        
	        System.out.println("----------------------------------");
	        DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
	        searchNode2 = (DefaultMutableTreeNode)root.getChildAt(lsIndex.get(lsIndex.size() - 1));
	        TreePath path = new TreePath(searchNode2.getPath());
	        tree.expandPath(path);
	        for (int i = lsIndex.size() - 2; i > 0; i--) {
	        	searchNode2 = (DefaultMutableTreeNode) searchNode2.getChildAt(lsIndex.get(i));
	        	path = new TreePath(searchNode2.getPath());
	        	tree.expandPath(path);
			}
	        searchNode2 = (DefaultMutableTreeNode) searchNode2.getChildAt(lsIndex.get(0));
	        path = new TreePath(searchNode2.getPath());
	        
	        tree.setSelectionPath(path);
		} else {
			mainFrame.getCreateReport().createReport(mainFrame);
		}
		
		mainFrame.setVisible(true);	
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		new Main();	
	}
}
