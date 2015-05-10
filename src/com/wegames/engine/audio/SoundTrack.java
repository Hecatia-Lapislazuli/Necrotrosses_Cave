package com.wegames.engine.audio;

import java.util.ArrayList;
import java.util.List;

public class SoundTrack {

	private class Note {
		Sound sound;
		int duration;
		double volume;
		Note(Sound sound, int duration, double volume) {
			this.sound = sound;
			this.duration = duration;
			this.volume = volume;
		}
	}
	
	private Sound[] notes;
	private List<Note> track = new ArrayList<Note>();
	private boolean playing = false;
	
	public SoundTrack(Sound[] notes) {
		this.notes = notes.clone();
	}
	
	public void addNote(int index, int ms, double volume) {
		track.add(new Note(notes[index], ms, volume));
	}
	
	public void addSilentNote(int ms) {
		track.add(new Note(null, ms, 0.0));
	}
	
	public void play() {
		new Thread("SoundTrack") {
			public void run() {
				for (Note note : track) {
					note.sound.play(note.volume);
					try {
						Thread.sleep(note.duration);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	private void playNoThread() {
		for (Note note : track) {
			if (note.sound != null)
				note.sound.play(note.volume);
			try {
				Thread.sleep(note.duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loop() {
		playing = true;
		new Thread("SoundTrack") {
			public void run() {
				while (playing)
					playNoThread();
			}
		}.start();
	}
	
	public void stop() {
		playing = false;
	}


}
