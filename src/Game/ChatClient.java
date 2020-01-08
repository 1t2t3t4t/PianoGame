package Game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatClient extends JFrame{
	private Socket socket;
	private String Username;
	private ArrayList<Integer> sequence = new ArrayList();
	private DataInputStream input;
	private DataOutputStream output;
	JButton enter = new JButton("Send");
	JTextField inbox = new JTextField();
	JTextArea chat = new JTextArea();
	
	public ChatClient(String username){
		Username = username;
		setGUI();
		try {
			socket = new Socket("localhost", 9001);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		enter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String msgToSend = inbox.getText();
				try {
					output.writeUTF(Username+" : "+msgToSend);
					inbox.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		
		Thread handleMessage = new Thread(){
			
			public void run(){
				while(true){
					try {
						String in = input.readUTF();
						String message = chat.getText()+"\n"+in;
						chat.setText(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		handleMessage.start();
	}
	
	public void setGUI(){
		this.setLayout(new BorderLayout());
		this.add(chat,BorderLayout.CENTER);
		this.add(enter, BorderLayout.EAST);
		this.add(inbox, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(350, 250));
		this.setVisible(true);
	}
	
}
