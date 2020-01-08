package Game;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Server {
	ServerSocket serverSocket;
	ArrayList<Client> clients = new ArrayList();
	ArrayList<Client> waitingList = new ArrayList();
	int count = 0;
	JLabel status;
	JFrame windows;

	public static void main(String[] args) {
		try {
			new Server();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Server() throws IOException {
		setServerGraphic();
		serverSocket = new ServerSocket(9000);
		status.setText("Server is on.");
		Thread handleClient = new Thread() {
			public void run() {
				while (true) {
					System.out.println("Waiting for client");
					Client client1 = new Client();
					if (!client1.socket.isClosed()) {
						clients.add(client1);
					}
					status.setText("Number of active player is "
							+ clients.size());
					System.out.println("Someone has joined");
					if (waitingList.size() == 2) {
						System.out.println("Paired");
						try {
							new gameRoom(waitingList.get(0), waitingList.get(1));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						waitingList.remove(0);
						waitingList.remove(0);
					}
				}
			}
		};
		handleClient.start();
	}

	void setServerGraphic() {
		windows = new JFrame();
		windows.setLayout(new FlowLayout());
		windows.setPreferredSize(new Dimension(500, 150));
		status = new JLabel();
		status.setPreferredSize(new Dimension(300, 100));
		windows.add(status);
		windows.setVisible(true);
		windows.pack();
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new ChatHost();
	}

	class Client {
		String name;
		Socket socket;
		DataInputStream input;
		DataOutputStream output;

		Client() {
			try {
				socket = serverSocket.accept();
				System.out.println("Someone Join server");
				output = new DataOutputStream(socket.getOutputStream());
				input = new DataInputStream(socket.getInputStream());
				this.name = input.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (input.readUTF().equals("join")) {
					waitingList.add(this);
					System.out.println("Finding match");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread active = new Thread() {
				public void run() {
					while (true) {
						System.out.println(socket.isInputShutdown());
					}
				}
			};
			active.start();
		}

	}

	class gameRoom {
		Client client1;
		Client client2;
		private int round = 0;

		gameRoom(Client client1, Client client2) throws IOException {
			count++;
			this.client1 = client1;
			this.client2 = client2;
			this.client1.output.writeUTF(client2.name);
			this.client2.output.writeUTF(client1.name);
			JButton resetroom = new JButton("Reset room " + count);
			resetroom.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					round = 4;
				}
			});
			windows.add(resetroom);
			windows.repaint();
			System.out.println("Room is created.");
			if (Math.random() <= 0.5) {
				System.out.println("Working");
				this.client1.output.writeUTF("yourturn");
				this.client2.output.writeUTF("opponentturn");
				new Thread(new waitingForSequence(client1, client2)).start();
				int score = this.client2.input.readInt();
				switchTurn(client2, client1, score, 0);

			} else {
				this.client2.output.writeUTF("yourturn");
				this.client1.output.writeUTF("opponentturn");
				new Thread(new waitingForSequence(client2, client1)).start();
				int score = this.client1.input.readInt();
				switchTurn(client1, client2, score, 0);
			}
		}

		private void switchTurn(Client client, Client target, int clientScore,
				int targetScore) {
			System.out.println("Switch turn");
			if (client.socket.isClosed() && target.socket.isClosed()) {
				return;
			} else if (client.socket.isClosed()) {
				try {
					target.output.writeUTF("sessiondone");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (target.socket.isClosed()) {
				try {
					client.output.writeUTF("sessiondone");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (round >= 4) {
				round = 0;
				if (clientScore >= targetScore) {
					try {
						client.output.writeUTF("You win!!");
						target.output.writeUTF("You lose!!");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						target.output.writeUTF("You win!!");
						client.output.writeUTF("You lose!!");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					boolean rematch1 = target.input.readBoolean();
					boolean rematch2 = client.input.readBoolean();
					System.out.println("Rematch? " + rematch1 + " " + rematch2);
					if (rematch1 && rematch2) {
						new gameRoom(client, target);
					} else if (rematch1) {
						target.output.writeUTF("sessiondone");
					} else if (rematch2) {
						client.output.writeUTF("sessiondone");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
			try {
				client.output.writeUTF("yourturn");
				target.output.writeUTF("opponentturn");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new Thread(new waitingForSequence(client, target)).start();
			round++;
			try {
				targetScore = target.input.readInt();
				switchTurn(target, client, targetScore, clientScore);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		class waitingForSequence implements Runnable {
			Client client;
			Client target;

			public waitingForSequence(Client client, Client target) {
				// TODO Auto-generated constructor stub
				this.client = client;
				this.target = target;
			}

			public void run() {
				ArrayList<Integer> sequence = new ArrayList();
				while (true) {
					try {
						int note = client.input.readInt();
						sequence.add(note);
						if (note == 99) {
							break;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						try {
							target.output.writeInt(404);
							break;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							return;
						}
					}
				}
				try {
					sendSequence(target, sequence);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					try {
						client.output.writeUTF("sessiondone");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			void sendSequence(Client client, ArrayList<Integer> sequence)
					throws IOException {
				System.out.println("Sending");
				for (int note : sequence) {
					client.output.writeInt(note);
				}
			}
		}
	}
}
