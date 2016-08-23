package model;
import java.awt.SystemTray;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.ClipboardListener;
import view.SystemTrayView;

public class Main {

	public static void main(String args[]) {
		ClipMaster cm = new ClipMaster();
		ClipboardListener cl = new ClipboardListener(cm);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					SystemTrayView stv = new SystemTrayView(SystemTray.getSystemTray(), Toolkit.getDefaultToolkit(), cm);
					stv.createAndDisplayView();
					new Thread(cl).start();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
