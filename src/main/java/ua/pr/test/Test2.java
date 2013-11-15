package ua.pr.test;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Test2 {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JFrame frm = new JFrame();
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar mb = new JMenuBar();
		frm.getContentPane().add(mb);
		JMenu m = new JMenu("Menu");
		mb.add(m);
		JMenuItem mi = new JMenuItem("Excel");
		mi.setIcon(new ImageIcon("d:/TempNet/ico/bmp.png"));
		m.add(mi);
		frm.pack();
		frm.setVisible(true);
		
		mi.setBorder(new EmptyBorder(0,-12,0,-10));
			
//		border=javax.swing.plaf.metal.MetalBorders$MenuItemBorder@18b8914]
//	w	border=javax.swing.plaf.basic.BasicBorders$MarginBorder@d3c65d]
	}

}
