package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/* 
 * ClipMaster is a utility class for managing all current clips.
 * When this object is changed, the view observer is notified.
 */
public class ClipMaster extends Observable {

	List<String> clips;

	public ClipMaster() {
		clips = new ArrayList<>();
	}

	public void addClip(String c) {
		if (!clips.contains(c)) {
			clips.add(c);
			changed();
		}
	}

	public void removeClip(String c) {
		clips.remove(c);
		changed();
	}

	public void clearClips() {
		clips.clear();
	}

	public String getLastClip() {
		return clips.get(clips.size() - 1);
	}

	public List<String> getClips() {
		return clips;
	}

	private void changed() {
		setChanged();
		notifyObservers();
	}
}
