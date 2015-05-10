/**
 * 
 */
package com.wegames;

import java.io.File;
import java.io.FileOutputStream;

import com.wegames.engine.Display;
import com.wegames.engine.audio.Sound;
import com.wegames.engine.audio.SoundTrack;
import com.wegames.engine.entity.Necromancer;
import com.wegames.engine.entity.Player;
import com.wegames.engine.graphics.Sprite;
import com.wegames.engine.util.vector.Vector2D;
/**
 * @author Lord Lucumnox
 *
 */
public class Main extends Display {
	private static final long serialVersionUID = 1L;

	public Main(int width, int height) {
		super(width, height);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		FileOutputStream stream = new FileOutputStream(new File("test.txt"));
		
		
		Sound[] sounds = new Sound[12];
		for (int i = 0; i < sounds.length; i++)
			sounds[i] = new Sound("res/bit" + i + ".wav");

		SoundTrack track = new SoundTrack(sounds);
		track.addNote(0, 820, 0.6);
		track.addNote(1, 820, 1);
		track.addNote(0, 820, 0.6);
		track.addNote(2, 820, 1);
		track.addNote(0, 820, 0.6);
		track.addNote(3, 820, 1);
		track.addNote(2, 580, 0.8);
		track.addNote(1, 580, 0.6);
		track.addNote(0, 580, 0.4);
		track.addSilentNote(1000);
		
		track.loop();

		Main main = new Main(1080, 1080*9/16);
		main.setPlayer(new Player(main, Sprite.player, 0, 0));
		main.start();
		
		main.map.findPath(main.player, new Vector2D(4, 4));
		
		new Necromancer(main, Sprite.magma, 64, 64);
	}

}
