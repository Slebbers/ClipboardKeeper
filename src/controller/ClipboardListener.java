package controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import model.ClipMaster;

/* ClipboardListener listens for changes to the clipboard and
 * updates ClipMaster with new content.
 */
public class ClipboardListener implements ClipboardOwner, Runnable {

	Clipboard clipboard;
	ClipMaster cm;

	public ClipboardListener(ClipMaster cm) {
		this.cm = cm;
		this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	public void run() {
		Transferable t = clipboard.getContents(this);
		regainOwnership(t);
	}

	public void lostOwnership(Clipboard c, Transferable t) {
		/*
		 * http://www.coderanch.com/t/377833/java/java/listen-clipboard 
		 * Exception is thrown when attempting to get the clipboard contents
		 * after another application has gained ownership. Adding a small delay
		 * fixes this issue. Thanks to Marc Weber on coderanch for this solution.
		 */
		try {
			Thread.sleep(20);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		
		Transferable contents = clipboard.getContents(this); // EXCEPTION
		regainOwnership(contents);
		updateClips();
	}

	private void updateClips() {
		try {
			cm.addClip((String) clipboard.getData(DataFlavor.stringFlavor));
		} catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
	}

	private void regainOwnership(Transferable t) {
		clipboard.setContents(t, this);
	}
}
