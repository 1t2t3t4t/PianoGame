package Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;

public class Client extends JFrame {
	private Socket socket;
	private ArrayList<Integer> sequence = new ArrayList();
	private Piano piano = new Piano();
	private JLabel status = new JLabel();
	private DataInputStream input;
	private DataOutputStream output;
	private JOptionPane dialog;
	private String name;
	private String opponentName;

	public Client(String name) throws InterruptedException {
		setGraphic();
		try {
			socket = new Socket("localhost", 9000);
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			this.name = name;
			output.writeUTF(name);
			joinRoom();
			status.setText("Wait for room...");
			dialog.showMessageDialog(null, "Welcome to the piano game"+"\n"+"Please wait for other player", "WELCOME!!", dialog.WARNING_MESSAGE);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(null, 
		            "Are you sure to close the game?", "Really Closing?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	try {
						socket.shutdownInput();
						socket.shutdownOutput();
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            //System.exit(0);
		        }
		    }
		});
	}

	void setGraphic() {
		piano.setBounds(100, 0, 480, 240);
		status.setBounds(240, 300, 400, 100);
		this.setLayout(null);
		this.add(status);
		status.setText("Waiting...");
		this.add(piano);
		this.setVisible(true);
		this.addKeyListener(new keyListener());
		this.setPreferredSize(new Dimension(800, 400));
		dialog = new JOptionPane();
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void joinRoom() {
		try {
			output.writeUTF("join");
			opponentName = input.readUTF();
			new Thread(new turnOrder()).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void gameStart() {
		new Thread(new turnOrder()).start();
	}

	class turnOrder implements Runnable {
		public void run() {
			try {
				String order = input.readUTF();
				if (order.equals("yourturn")) {
					new Thread(new myTurn()).start();
				} else if (order.equals("opponentturn")) {
					new Thread(new oppnentTurn()).start();
				} else if (order.equals("sessiondone")){
					dialog.showMessageDialog(null, "Opponent left the match.", "Game over", dialog.ERROR_MESSAGE);
				}
				else {
					int choice = dialog.showConfirmDialog(null, order,
							"Do you want to rematch? "+5, dialog.YES_NO_OPTION);
					int timer = 5;
					while (timer >= 0) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						timer--;
						if (choice == dialog.YES_OPTION) {
							output.writeBoolean(true);
							new Thread(new turnOrder()).start();
							return;
						} else {
							output.writeBoolean(false);
							setVisible(false);
							return;
						}
					}
					dialog.disable();
					output.writeBoolean(false);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class oppnentTurn implements Runnable {
		public void run() {
			// setEnabled(false);
			ArrayList<Integer> Sequence = new ArrayList();
			status.setText("Wait for opponent  (Match up with "+opponentName+")");
			while (true) {
				try {
					int note = input.readInt();
					System.out.println("Note receieved " + note);
					if(note ==404){
						dialog.showMessageDialog(null, "Opponent left the match.", "Game over", dialog.ERROR_MESSAGE);
						socket.close();
						return;
					}
					if (note == 99)
						break;
					Sequence.add(note);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				System.out.println("Play sequence");
				piano.playSequence(Sequence);
			} catch (MidiUnavailableException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// setEnabled(true);
			System.out.println(isEnabled());
			sequence.removeAll(sequence);
			Double time = 0.0;
			long start_time = System.nanoTime();
			while (time < 20.0) {
				time = (System.nanoTime() - start_time)/1e9;
				if (Sequence.size() <= sequence.size())
					break;
				status.setText("Start guessing " + time);
			}
			// setEnabled(false);
			int point = 0;
			for (int i = 0; i < sequence.size(); i++) {
				if (Sequence.get(i) == sequence.get(i))
					point += 10;
				else
					point -= 20;
			}
			status.setText("Your Score " + point);
			sequence.removeAll(sequence);
			try {
				Thread.sleep(1000);
				output.writeInt(point);
				new Thread(new turnOrder()).start();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class myTurn implements Runnable {
		public void run() {
			// setEnabled(true);
			status.setText("Start (Match up with "+opponentName+")");
			int time = 0;
			sequence.clear();
			while (time < 10) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				time++;
				status.setText("Start " + time);
			}
			status.setText("Stop");
			// setEnabled(false);
			piano.clearNote();
			for (int note : sequence) {
				try {
					output.writeInt(note);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				output.writeInt(99);
				new Thread(new turnOrder()).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Client client = new Client("lol");
		client.pack();
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
