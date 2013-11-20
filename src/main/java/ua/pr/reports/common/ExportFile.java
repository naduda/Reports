package ua.pr.reports.common;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ExportFile {
	private CreateReport cr;
	
	public ExportFile(CreateReport cr) {
		this.cr = cr;
	}
	
	public void exequte(byte format) {
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Сохранение файла");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int res = fc.showSaveDialog(new JFrame());

		if ( res == JFileChooser.APPROVE_OPTION ) {
			cr.create(format, fc.getSelectedFile().getPath());
		}
	}
}
