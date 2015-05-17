/**
 * 
 */
package com.wegames;

import javax.sound.midi.*;

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

	private static void synth(boolean loop) throws Exception {
		new Thread() {
			public void run() {
				Synthesizer synth = null;
				try {
					synth = MidiSystem.getSynthesizer();
					synth.open();
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
					throw new RuntimeException("Synth failed to open!");
				}

				MidiChannel[] channels = synth.getChannels();
				synth.loadAllInstruments(synth.getDefaultSoundbank());

				int channel = 0;
				channels[channel].programChange(0); // Instrument

				do {
					try {
						channels[channel].noteOn(60, 80); // C
						Thread.sleep(500); // Note duration!
						channels[channel].noteOff(60, 85);
	
						channels[channel].noteOn(62, 85); // D
						Thread.sleep(500);
						channels[channel].noteOff(62, 85); // D
	
						channels[channel].noteOn(64, 90); // E
						Thread.sleep(500);
						channels[channel].noteOff(64, 85);
	
						channels[channel].noteOn(65, 95); // F
						Thread.sleep(500);
						channels[channel].noteOff(65, 85);
	
						channels[channel].noteOn(67, 100); // G
						Thread.sleep(500);
						channels[channel].noteOff(67, 85);
	
						channels[channel].noteOn(69, 105); // A
						Thread.sleep(500);
						channels[channel].noteOff(69, 85);
	
						channels[channel].noteOn(71, 110); // B
						Thread.sleep(500);
						channels[channel].noteOff(71, 85);
	
						channels[channel].noteOn(72, 120); // C
						Thread.sleep(1500);
						channels[channel].noteOff(72, 120); // C
					} catch (InterruptedException e) {
					}
				} while (loop);

				synth.close();
			}
		}.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		synth(true);

		/*
		 * Sound[] sounds = new Sound[12]; for (int i = 0; i < sounds.length;
		 * i++) sounds[i] = new Sound("res/bit" + i + ".wav");
		 * 
		 * SoundTrack track = new SoundTrack(sounds); track.addNote(0, 820,
		 * 0.6); track.addNote(1, 820, 1); track.addNote(0, 820, 0.6);
		 * track.addNote(2, 820, 1); track.addNote(0, 820, 0.6);
		 * track.addNote(3, 820, 1); track.addNote(2, 580, 0.8);
		 * track.addNote(1, 580, 0.6); track.addNote(0, 580, 0.4);
		 * track.addSilentNote(1000);
		 * 
		 * track.loop();
		 */

		Main main = new Main(1080, 1080 * 9 / 16);
		main.setPlayer(new Player(main, Sprite.player, 0, 0));
		main.start();

		main.map.findPath(main.player, new Vector2D(4, 4));

		new Necromancer(main, Sprite.magma, 64, 64);
	}

}
