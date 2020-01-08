package Game;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Survival extends JFrame {
	private Piano piano;
	private JLabel label;
	private Random random;
	private ArrayList<Integer> sequence = new ArrayList();

	Survival() throws InterruptedException {
		piano = new Piano();
		label = new JLabel("WelCome!!!");
		setLayout(null);
		addKeyListener(new keyListener());
		piano.setBounds(10, 10, 480, 240);
		label.setBounds(10, 280, 300, 50);
		add(piano);
		add(label);
		this.setPreferredSize(new Dimension(800, 400));
		setVisible(true);
		Thread.sleep(2000);
		botTurn(1);
	}

	void botTurn(int round) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int notes = 2 + round;
		random = new Random();
		ArrayList<Integer> sequence = new ArrayList();
		for (int i = 0; i < notes; i++) {
			sequence.add(random.nextInt(13));
		}
		try {
			piano.playSequence(sequence);
		} catch (MidiUnavailableException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (playerTurn(round, sequence))
			botTurn(round++);

	}

	boolean playerTurn(int round, ArrayList<Integer> sequence) {
		label.setText("Start ");
		this.sequence.clear();
		Double time = 0.0;
		long start_time = System.nanoTime();
		while (time < 20.0) {
			time = (System.nanoTime() - start_time) / 1e9;
			for (int i = 0; i < this.sequence.size(); i++) {
				if (this.sequence.get(i) != sequence.get(i)) {
					JOptionPane dialog = new JOptionPane();
					dialog.showMessageDialog(null,
							"You lose in round " + round, "Lose",
							dialog.ERROR_MESSAGE);
					return false;
				}
			}
			if (sequence.size() <= this.sequence.size())
				break;
			label.setText("Start guessing " + time);
		}
		return true;
	}

	public static void main(String[] args) {
		try {
			new Survival().pack();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class keyListener implements KeyListener {

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			switch (e.getKeyChar()) {
			case 's':
				piano.keys[0].keyReleased();
				break;
			case 'e':
				piano.keys[1].keyReleased();
				break;
			case 'd':
				piano.keys[2].keyReleased();
				break;
			case 'r':
				piano.keys[3].keyReleased();
				break;
			case 'f':
				piano.keys[4].keyReleased();
				break;
			case 'g':
				piano.keys[5].keyReleased();
				break;
			case 'y':
				piano.keys[6].keyReleased();
				break;
			case 'h':
				piano.keys[7].keyReleased();
				break;
			case 'u':
				piano.keys[8].keyReleased();
				break;
			case 'j':
				piano.keys[9].keyReleased();
				break;
			case 'i':
				piano.keys[10].keyReleased();
				break;
			case 'k':
				piano.keys[11].keyReleased();
				break;
			case 'l':
				piano.keys[12].keyReleased();
				break;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			piano.clearNote();
			switch (e.getKeyChar()) {
			case 's':
				piano.keys[0].keyPressed();
				sequence.add(0);
				break;
			case 'e':
				piano.keys[1].keyPressed();
				sequence.add(1);
				break;
			case 'd':
				piano.keys[2].keyPressed();
				sequence.add(2);
				break;
			case 'r':
				piano.keys[3].keyPressed();
				sequence.add(3);
				break;
			case 'f':
				piano.keys[4].keyPressed();
				sequence.add(4);
				break;
			case 'g':
				piano.keys[5].keyPressed();
				sequence.add(5);
				break;
			case 'y':
				piano.keys[6].keyPressed();
				sequence.add(6);
				break;
			case 'h':
				piano.keys[7].keyPressed();
				sequence.add(7);
				break;
			case 'u':
				piano.keys[8].keyPressed();
				sequence.add(8);
				break;
			case 'j':
				piano.keys[9].keyPressed();
				sequence.add(9);
				break;
			case 'i':
				piano.keys[10].keyPressed();
				sequence.add(10);
				break;
			case 'k':
				piano.keys[11].keyPressed();
				sequence.add(11);
				break;
			case 'l':
				piano.keys[12].keyPressed();
				sequence.add(12);
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
