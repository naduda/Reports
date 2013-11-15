package ua.pr.reports.ui.tools;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.BevelBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import oracle.xdo.template.FOProcessor;
import ua.pr.common.ToolsPrLib;
import ua.pr.model.ModelDB;
import ua.pr.model.orm.Account;
import ua.pr.model.orm.LinkAccount;
import ua.pr.model.orm.Meter;
import ua.pr.model.orm.Substation;
import ua.pr.reports.CreateReport;
import ua.pr.reports.Main;
import ua.pr.reports.common.Tools;
import ua.pr.reports.common.Template;
import ua.pr.ui.login.ILogin;

public class SetFrameComponent {
	private DefaultMutableTreeNode selectedNode = null;
	private ModelDB mdb;
	private DefaultTreeModel treeModel;
	
	public SetFrameComponent() {
		mdb = new ModelDB();
	}
	
	public ModelDB getMdb() {
		return mdb;
	}
	
	public void setReports(JComponent menu, List<JMenuItem> items, CreateReport cReport) {
		if (menu.getClass() == JMenuBar.class) {
			for (int i = 0; i < menu.getComponentCount(); i++) {
				setReports((JComponent) menu.getComponent(i), items, cReport);
			}		
		} else if (menu.getClass() == JMenu.class) {
			for (int i = 0; i < ((JMenu)menu).getItemCount(); i++) {
				JMenuItem item = ((JMenu)menu).getItem(i);
				if (item.getClass() == JMenuItem.class) {
					Template template = new Template();
					
					template.setDataTemplate(ToolsPrLib.getFullPath((String) item.getClientProperty("dataTemplate")));
					template.setId((String) item.getClientProperty("uniqueID"));
					template.setName((String) item.getClientProperty("name"));
					template.setParams((String) item.getClientProperty("params"));
					template.setQuery((String) item.getClientProperty("query"));
					template.setRtfTemplate(ToolsPrLib.getFullPath((String) item.getClientProperty("rtfTemplate")));
					
					String uniqueID = (String) item.getClientProperty("uniqueID");
					if (uniqueID.indexOf("Export") >= 0) {
						String format = (String) item.getClientProperty("format");
						Byte fopFormat = 0;
						if (format.toLowerCase().equals("excel")) {
							fopFormat = FOProcessor.FORMAT_EXCEL;
						} else if (format.toLowerCase().equals("pdf")) {
							fopFormat = FOProcessor.FORMAT_PDF;
						} else if (format.toLowerCase().equals("gif")) {
							fopFormat = FOProcessor.FORMAT_IMAGE_GIF;
						} else if (format.toLowerCase().equals("rtf")) {
							fopFormat = FOProcessor.FORMAT_RTF;
						}
						
						item.addActionListener(new ActionListenerJMenuItem(fopFormat, cReport));
					} else {
						item.addActionListener(new ActionListenerJMenuItem(template, Main.lbActiveReport, cReport));
					}
					items.add(item);
					String def = (String) item.getClientProperty("default");
					if ((def != null) && (def.toString().toLowerCase().equals("true"))) {
						Main.cr.setTemplate(template);
						Main.lbActiveReport.setText(template.getName());
					}
				} else if (item.getClass() == JMenu.class) {
					setReports((JMenu)item, items, cReport);
				}
			}
		}
	}

	public void setStatusBar(JPanel panel, ILogin login) {
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		String status = "Сервер - %s; База даних - %s; Користувач - %s.";
		JLabel lb = new JLabel(String.format(status, login.getServer(), login.getDB(), login.getUser()));
		panel.add(lb);
	}
	
	public void setTree(CreateReport cRep) {
		String rootText;
		try {
			rootText = mdb.getSubstationById(0).getName();
		} catch (Exception e) {
			rootText = "Підстанції";
		}
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootText);
		treeModel = new DefaultTreeModel(root, true);
//		-----------------------------------------------------------------------------		
		List<Substation> allSubstations = mdb.allSubstations();
		for (Substation s : allSubstations) {
			DefaultMutableTreeNode nodeSubstation = new DefaultMutableTreeNode(s, true);
			root.add(nodeSubstation);
		}
//		-----------------------------------------------------------------------------
		JTree tree = new JTree(treeModel);
		tree.addTreeSelectionListener(new SelectionL(cRep));
		tree.addTreeExpansionListener(new ExpansionL());
		tree.setCellRenderer(new NodeIcons());
		Main.treePanel.setViewportView(tree);
	}
	
	class NodeIcons extends DefaultTreeCellRenderer {		
		private static final long serialVersionUID = 1L;
		private Icon icon;

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
			Object o = ((DefaultMutableTreeNode) value).getUserObject();
			if (o.getClass() == Substation.class) {
				Substation s = (Substation)o;

				icon = new ImageIcon("d:/TempNet/ico/Substation.png");
				setIcon(icon);
				setText(s.getName());
			} else if (o.getClass() == Account.class) {
				Account a = (Account)o;

				icon = new ImageIcon("d:/TempNet/ico/account.png");
				setIcon(icon);
				setText(a.getCapt());
			} else if (o.getClass() == Meter.class) {
				Meter m = (Meter)o;

				icon = new ImageIcon("d:/TempNet/ico/Meter.png");
				setIcon(icon);
				setText(m.getCapt());
			} else {
				setIcon(null);
				setText("   " + value);
            }
			
			return this;
		}
		
	}
	
	class ActionListenerJMenuItem implements ActionListener {
		private Template template;
		private JLabel lbActiveReport;
		private CreateReport cRep;
		private boolean export;
		private byte format;

		public ActionListenerJMenuItem(Template template, JLabel lbActiveReport, CreateReport cRep) {
			this.template = template;
			this.lbActiveReport = lbActiveReport;
			this.cRep = cRep;
			setExport(false);
		}
		
		public ActionListenerJMenuItem(byte format, CreateReport cRep) {
			this.cRep = cRep;
			this.format = format;
			setExport(true);
		}
		
		public void actionPerformed(ActionEvent e) {
			if (export) {
				Tools exp = new Tools(cRep);
				exp.exportFile(format);
			} else {
				lbActiveReport.setText(template.getName());
				lbActiveReport.setPreferredSize(lbActiveReport.getMaximumSize());
	
				cRep.setTemplate(template);
				try {
					cRep.createReport();
				} catch (Exception e2) {
					System.err.println("CreateReport.create().....");
				}
			}
		}		
		
		public boolean isExport() {
			return export;
		}

		public void setExport(boolean export) {
			this.export = export;
		}
	}
	
	class ExpansionL implements TreeExpansionListener {
		public void treeCollapsed(TreeExpansionEvent arg0) {
			
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
	
	class SelectionL implements TreeSelectionListener {
		private CreateReport cRep;
		
		public SelectionL(CreateReport cRep) {
			this.cRep = cRep;
		}
		
		public void valueChanged(TreeSelectionEvent e) {
			JTree tree = (JTree)e.getSource();
			TreePath[] selected = tree.getSelectionPaths();

			TreePath path = selected[0];

			selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
			Main.selectedObject = selectedNode.getUserObject();
			
			cRep.createReport();
		}
	}
}
