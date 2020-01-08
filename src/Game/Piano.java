package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.JLayeredPane;

public class Piano extends JLayeredPane {

	Key keys[] = { new Key("C", 60), new Key("C#", 61), new Key("D", 62),
			new Key("D#", 63), new Key("E", 64), new Key("F", 65),
			new Key("F#", 66), new Key("G", 67), new Key("G#", 68),
			new Key("A", 69), new Key("A#", 70), new Key("B", 71),
			new Key("c", 72) };

	public Piano() throws InterruptedException {
		this.setLayout(null);
		this.addKeyListener(new keyListener());
		setKeys();
	}

	void setKeys() throws InterruptedException {
		int normal = 0;
		for (int i = 0; i < keys.length; i++) {
			if (i % 2 != 0 && i < 5 && i > 0) {
				keys[i].setBounds(normal - 15, 0, keys[i].getWidth(),
						keys[i].getHeight());
				this.add(keys[i], new Integer(1), 0);
			} else if (i % 2 == 0 && i > 5 && i <= 10) {
				keys[i].setBounds(normal - 15, 0, keys[i].getWidth(),
						keys[i].getHeight());
				this.add(keys[i], new Integer(1), 0);
			} else {
				keys[i].setBounds(normal, 0, keys[i].getWidth(),
						keys[i].getHeight());
				this.add(keys[i], new Integer(0), 0);
				normal += 60;
			}
			keys[i].addKeyListener(new keyListener());
		}
	}

	void clearNote() {
		for (Key key : keys) {
			if (key.isPressed()) {
				key.keyReleased();
			}
		}
	}

	void playSequence(ArrayList<Integer> sequence)
			throws MidiUnavailableException, InterruptedException {
		for (int note : sequence) {
			System.out.println("Note played " + note);
			keys[note].autoPlay();
		}
	}

	class keyListener implements KeyListener {

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			switch (e.getKeyChar()) {
			case 's':
				keys[0].keyReleased();
				break;
			case 'e':
				keys[1].keyReleased();
				break;
			case 'd':
				keys[2].keyReleased();
				break;
			case 'r':
				keys[3].keyReleased();
				break;
			case 'f':
				keys[4].keyReleased();
				break;
			case 'g':
				keys[5].keyReleased();
				break;
			case 'y':
				keys[6].keyReleased();
				break;
			case 'h':
				keys[7].keyReleased();
				break;
			case 'u':
				keys[8].keyReleased();
				break;
			case 'j':
				keys[9].keyReleased();
				break;
			case 'i':
				keys[10].keyReleased();
				break;
			case 'k':
				keys[11].keyReleased();
				break;
			case 'l':
				keys[12].keyReleased();
				break;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			clearNote();
			switch (e.getKeyChar()) {
			case 's':
				keys[0].keyPressed();
				break;
			case 'e':
				keys[1].keyPressed();
				break;
			case 'd':
				keys[2].keyPressed();
				break;
			case 'r':
				keys[3].keyPressed();
				break;
			case 'f':
				keys[4].keyPressed();
				break;
			case 'g':
				keys[5].keyPressed();
				break;
			case 'y':
				keys[6].keyPressed();
				break;
			case 'h':
				keys[7].keyPressed();
				break;
			case 'u':
				keys[8].keyPressed();
				break;
			case 'j':
				keys[9].keyPressed();
				break;
			case 'i':
				keys[10].keyPressed();
				break;
			case 'k':
				keys[11].keyPressed();
				break;
			case 'l':
				keys[12].keyPressed();
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
