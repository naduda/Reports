package ua.pr.reports.ui;

import java.awt.Component;
import java.awt.Container;
import java.io.Serializable;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import ua.pr.common.ToolsPrLib;
import ua.pr.model.ModelDB;
import ua.pr.model.orm.Account;
import ua.pr.model.orm.LinkAccount;
import ua.pr.model.orm.Meter;
import ua.pr.model.orm.Substation;
import ua.pr.reports.common.CreateReport;
import ua.pr.reports.xml.objects.Base;

public class TreePanel extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private static String icoFolder;
	private DefaultTreeModel treeModel;	
	private DefaultMutableTreeNode selectedNode;
	private JTree tree;
	private CreateReport createReport;

	public TreePanel(ModelDB mdb, Base base) {
		icoFolder = ToolsPrLib.getFullPath(base.getMainForm().getIcoPath());
		String rootText;
		try {
			rootText = mdb.getSubstationById(0).getName();
		} catch (Exception e) {
			rootText = "Підстанції";
		}
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootText);
		treeModel = new DefaultTreeModel(root, true);
	//	-----------------------------------------------------------------------------		
		List<Substation> allSubstations = mdb.allSubstations();
		for (Substation s : allSubstations) {
			DefaultMutableTreeNode nodeSubstation = new DefaultMutableTreeNode(s, true);
			root.add(nodeSubstation);
		}
	//	-----------------------------------------------------------------------------
		tree = new JTree(treeModel);
		tree.addTreeSelectionListener(new SelectionL());
		tree.addTreeExpansionListener(new ExpansionL());
		tree.setCellRenderer(new NodeIcons());
		setViewportView(tree);
	}
//	Getters and Setters ---------------------------------------------------------------
	public CreateReport getCreateReport() {
		return createReport;
	}

	public DefaultMutableTreeNode getSelectedNode() {
		return selectedNode;
	}
	
	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}
	
	public JTree getTree() {
		return tree;
	}

	public void setCreateReport(CreateReport createReport) {
		this.createReport = createReport;
	}
//	-----------------------------------------------------------------------------------
	class SelectionL implements TreeSelectionListener, Serializable {
		private static final long serialVersionUID = 1L;
		
		public SelectionL() {

		}
		
		public void valueChanged(TreeSelectionEvent e) {
			JTree tree = (JTree)e.getSource();
			TreePath[] selected = tree.getSelectionPaths();

			TreePath path = selected[0];

			selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
			
//			-------------------------------------
			Container mainFrame = (Container) e.getSource();
			while (!mainFrame.getClass().equals(MainFrame.class)) {
				mainFrame = mainFrame.getParent();
			}
//			-------------------------------------
			((MainFrame)mainFrame).setSelectedObject(selectedNode.getUserObject());

			createReport.createReport((MainFrame)mainFrame);
		}
	}
	
	class ExpansionL implements TreeExpansionListener, Serializable {
		private static final long serialVersionUID = 1L;
		
		public void treeCollapsed(TreeExpansionEvent e) {
			
		}

		public void treeExpanded(TreeExpansionEvent event) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
			if (node.getChildCount() == 0) {
				Object obj = node.getUserObject();
				if (obj.getClass() == Substation.class) {
					Substation s = (Substation) obj;
					for (Account a : s.getAccounts()) {
						DefaultMutableTreeNode nodeAccount = new DefaultMutableTreeNode(a, true);
						node.add(nodeAccount);
					}
					treeModel.reload(node);
				} else	if (obj.getClass() == Account.class) {
					Account a = (Account) obj;
					for (LinkAccount la : a.getLinkAccounts()) {
						for (Meter m : la.getMeters()) {
							DefaultMutableTreeNode nodeMeter = new DefaultMutableTreeNode(m, false);
							node.add(nodeMeter);
						}	
					}
					treeModel.reload(node);
				}
			}
		}		
	}
	
	class NodeIcons extends DefaultTreeCellRenderer implements Serializable {		
		private static final long serialVersionUID = 1L;
		
		private Icon icon;

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
			Object o = ((DefaultMutableTreeNode) value).getUserObject();
			if (o.getClass() == Substation.class) {
				Substation s = (Substation)o;

				icon = new ImageIcon(icoFolder + "/Substation.png");
				setIcon(icon);
				setText(s.getName());
			} else if (o.getClass() == Account.class) {
				Account a = (Account)o;

				icon = new ImageIcon(icoFolder + "/account.png");
				setIcon(icon);
				setText(a.getCapt());
			} else if (o.getClass() == Meter.class) {
				Meter m = (Meter)o;

				icon = new ImageIcon(icoFolder + "/Meter.png");
				setIcon(icon);
				setText(m.getCapt());
			} else {
				setIcon(null);
				setText("   " + value);
            }
			
			return this;
		}		
	}
}
