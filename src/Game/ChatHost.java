package Game;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;




public class ChatHost {
	ServerSocket serverSocket;
	ArrayList<clientChat> clients = new ArrayList();
	
	ChatHost(){
		try {
			serverSocket = new ServerSocket(9001);
			System.out.println("Server Created");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread handleClient = new Thread(){
			public void run(){
				while(true){
					clients.add(new clientChat());
					System.out.println("Someone join");
				}
			}
		};
		handleClient.start();
	}
	
	
	class clientChat{
		Socket socket;
		DataInputStream input;
		DataOutputStream output;
		
		clientChat(){
			try {
				socket = serverSocket.accept();
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread handleMessage = new Thread(){
				
				public void run(){
					while(true){
						try {
							String in = input.readUTF();
							for(clientChat c : clients){
								c.output.writeUTF(in);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
			handleMessage.start();
		}
		
		public void sendMessage(String message){
			try {
				output.writeUTF(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
