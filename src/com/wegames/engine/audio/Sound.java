package com.wegames.engine.audio;

import java.io.File;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
	
	private String path, name;
	private MediaPlayer player;
	
	static {
		new JFXPanel();
	}
	
	public Sound(String file) {
		path = file;
		create();
	}
	
	private void create() {
		File soundFile = new File(path);
		if (!soundFile.exists()) {
			System.err.println("Sound file '" + path + "' not found!");
			return;
		}
		String[] strings = path.split("/");
		name = strings[strings.length - 1];
		player = new MediaPlayer(new Media(soundFile.toURI().toString()));
	}
	
	public void play() {
		play(1.0);
	}
	
	public void play(double volume) {
		player.stop();
		player.setCycleCount(0);
		player.setVolume(volume);
		player.play();
	}
	
	public void stop() {
		player.stop();
	}
	
	public String toString() {
		return "Sound: '" + name + "'";
	}
	
	
}
