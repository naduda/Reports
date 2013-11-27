package ua.pr.reports.ui.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import oracle.xdo.template.FOProcessor;
import ua.pr.common.ToolsPrLib;
import ua.pr.model.orm.PgdiFileStorage;
import ua.pr.model.orm.UserSetting;
import ua.pr.reports.common.CreateReport;
import ua.pr.reports.common.ExportFile;
import ua.pr.reports.common.Template;
import ua.pr.reports.ui.MainFrame;
import ua.pr.ui.login.ILogin;

public class SetFrameComponent implements Serializable {
	private static final long serialVersionUID = 1L;

	private MainFrame mainFrame;
	
	public SetFrameComponent(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void setReports(JComponent menu, CreateReport cReport) {
		if (menu.getClass() == JMenuBar.class) {
			for (int i = 0; i < menu.getComponentCount(); i++) {
				setReports((JComponent) menu.getComponent(i), cReport);
			}		
		} else if (menu.getClass() == JMenu.class) {
			String menuName = (String) menu.getClientProperty("uniqueID");
			if (menuName.toLowerCase().indexOf("xlt") > 0) {
				UserSetting user = mainFrame.getMdb().getUser(mainFrame.getBase().getLogin().getUser());
				System.out.println("**************************************************");
				for (PgdiFileStorage fs : user.getPgdiFileStorages()) {
					if (fs.getExt().trim().toLowerCase().equals("xlt")) {
						try {
//							FileOutputStream fis = new FileOutputStream(new File("d:/" + fs.getDesc()+ ".xlt"));
//							fis.write(fs.getBinary());
//							fis.close();
							
							JMenuItem mItem = new JMenuItem(fs.getDesc());
							mItem.putClientProperty("file", fs.getBinary());
							mItem.addActionListener(new ActionListenerXLT());
							menu.add(mItem);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				System.out.println("**************************************************");
			}
			for (int i = 0; i < ((JMenu)menu).getItemCount(); i++) {
				JMenuItem item = ((JMenu)menu).getItem(i);
				Template template = null;
				if (item.getClass() == JMenuItem.class) {
					String dataTemplate = (String) item.getClientProperty("dataTemplate");
					if(dataTemplate != null) {
						template = new Template();
						
						template.setDataTemplate(ToolsPrLib.getFullPath(dataTemplate));
						template.setId((String) item.getClientProperty("uniqueID"));
						template.setName((String) item.getClientProperty("name"));
						template.setParams((String) item.getClientProperty("params"));
						template.setQuery((String) item.getClientProperty("query"));
						template.setRtfTemplate(ToolsPrLib.getFullPath((String) item.getClientProperty("rtfTemplate")));
						
						String def = (String) item.getClientProperty("default");
						if ((def != null) && (def.toString().toLowerCase().equals("true"))) {
							
							mainFrame.getCreateReport().setTemplate(template);
							mainFrame.getLbActiveReport().setText(template.getName());
						}
						
						item.addActionListener(new ActionListenerJMenuItem(template, mainFrame.getLbActiveReport(), cReport));
					}
					
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
					}		
				} else if (item.getClass() == JMenu.class) {
					setReports((JMenu)item, cReport);
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
		
	class ActionListenerJMenuItem implements ActionListener, Serializable {
		private static final long serialVersionUID = 1L;
		
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
				ExportFile exp = new ExportFile(cRep);
				exp.exequte(format);
			} else {
				lbActiveReport.setText(template.getName());
				lbActiveReport.setPreferredSize(lbActiveReport.getMaximumSize());
	
				cRep.setTemplate(template);
				try {
					cRep.createReport(mainFrame);
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
	
	class ActionListenerXLT implements ActionListener, Serializable {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			System.out.println("XLT clicked");
		}
	
	}
}
