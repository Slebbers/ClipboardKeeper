package controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* ActionListener to add the clicked clip as the active clipboard item
 * 
 */
public class ClipActionListener implements ActionListener {

	Clipboard clipboard;
	
	public ClipActionListener() {
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		StringSelection clip = new StringSelection(e.getActionCommand());
		clipboard.setContents(clip, clip);
	}
}
