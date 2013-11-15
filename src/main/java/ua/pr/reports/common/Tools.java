package ua.pr.reports.common;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import ua.pr.reports.CreateReport;

public class Tools {
	private CreateReport cr;
	
	public Tools(CreateReport cr) {
		this.cr = cr;
	}
	
	public void exportFile(byte format) {
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Сохранение файла");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int res = fc.showSaveDialog(new JFrame());

		if ( res == JFileChooser.APPROVE_OPTION ) {
			cr.create(format, fc.getSelectedFile().getPath());
		}
	}
}
