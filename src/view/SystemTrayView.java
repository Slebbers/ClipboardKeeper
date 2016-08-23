package view;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import controller.ClipActionListener;
import model.ClipMaster;

/* Primary system tray view for ClipboardKeeper
 */
public class SystemTrayView implements Observer {

	SystemTray tray;
	Toolkit toolkit;
	ClipMaster cm;
	Menu clips;

	public SystemTrayView(SystemTray tray, Toolkit toolkit, ClipMaster cm) {
		cm.addObserver(this);
		clips = new Menu("Clips");
		this.tray = tray;
		this.toolkit = toolkit;
		this.cm = cm;
	}

	public void createAndDisplayView() {
		if (!SystemTray.isSupported()) {
			JOptionPane.showMessageDialog(null, "System Tray is not supported on this OS.", "Not Supported!", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		/* URL is needed for JAR building */
		URL url = System.class.getResource("/images/clip.png");
		Image img = toolkit.getImage(url);
		PopupMenu trayPopupMenu = new PopupMenu();
		TrayIcon trayIcon = new TrayIcon(img, "ClipKeeper", trayPopupMenu);
		MenuItem clear = new MenuItem("Clear Clips");
		MenuItem close = new MenuItem("Close");
		

		trayPopupMenu.add(clips);
		trayPopupMenu.addSeparator();

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cm.clearClips();
				removeClips();
				trayIcon.displayMessage("Clips have been cleared!", "",TrayIcon.MessageType.INFO);
			}
		});
		
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		trayPopupMenu.add(clear);
		trayPopupMenu.add(close);

		trayIcon.setImageAutoSize(true);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	private void addNewClip() {
		String clipText = cm.getLastClip();
		MenuItem newClip = new MenuItem(clipText);
		newClip.setActionCommand(clipText);
		newClip.addActionListener(new ClipActionListener());
		clips.add(newClip);
	}

	private void removeClips() {
		clips.removeAll();
	}

	@Override
	public void update(Observable o, Object arg) {
		addNewClip();
	}
}