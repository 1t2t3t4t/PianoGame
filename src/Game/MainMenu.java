package Game;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

//import javazoom.jl.player.Player;

public class MainMenu extends JFrame {
	// private Player playMP3;
	private Image image;
	private String Username;
	private JLabel background;

	MainMenu() {
		JOptionPane dialog = new JOptionPane();
		Username = dialog.showInputDialog(null, "Insert Your name");
		// playMusic();
		this.setPreferredSize(new Dimension(750, 450));
		this.setLayout(null);
		try {
			image = ImageIO.read(new File("piano.jpg"));
			background = new JLabel(new ImageIcon(image));
			setContentPane(background);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		JButton start = new JButton("Start");
		JButton serverStatus = new JButton("Check server status");
		JButton chat = new JButton("Enter Chat Room");
		JButton freestyle = new JButton("Freestyle Mode");
		JButton survival = new JButton("Survival Mode");
		JButton changeBackground = new JButton("Change Background picture");
		JButton changeSong = new JButton("Change song");
		JLabel layer = new JLabel();
		layer.setText("Piano Duet");
		changeBackground.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Picture(JPEG)", "jpg");
				fc.setFileFilter(filter);
				int result = fc.showOpenDialog(null);
				if (fc.APPROVE_OPTION == result) {
					File file = fc.getSelectedFile();
					try {
						image = ImageIO.read(file);
						background.setIcon(new ImageIcon(image));
						setContentPane(background);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});
		changeSong.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Client client;
				try {
					// playMP3.close();
					client = new Client(Username);
					client.pack();
					client.setDefaultCloseOperation(EXIT_ON_CLOSE);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		serverStatus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Socket socket = new Socket("localhost", 9000);
					socket.close();
					dialog.showMessageDialog(null, "Server is on.", "Server",
							dialog.PLAIN_MESSAGE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("Fd");
					dialog.showMessageDialog(null, "Server is off.", "Server",
							dialog.ERROR_MESSAGE);
				}
			}
		});
		chat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ChatClient chatroom = new ChatClient(Username);
				chatroom.pack();
				chatroom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		freestyle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Double i = Math.random();
				System.out.println(i);
				// playMP3.close();
				if (i >= 0.5) {
					new keyboard();
				} else {
					new extrakeyboard();
				}
			}
		});
		survival.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					// playMP3.close();
					new Survival().pack();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		layer.setBounds(340, 20, 100, 25);
		start.setBounds(325, 60, 100, 25);
		chat.setBounds(300, 160, 150, 25);
		freestyle.setBounds(300, 200, 150, 25);
		survival.setBounds(300, 250, 150, 25);
		serverStatus.setBounds(300, 100, 150, 25);
		changeBackground.setBounds(0, 390, 220, 20);
		changeSong.setBounds(515, 390, 220, 20);
		add(start);
		add(layer);
		add(chat);
		add(serverStatus);
		add(freestyle);
		add(survival);
		add(changeBackground);
		add(changeSong);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainMenu().pack();
	}

}
