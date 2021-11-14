package final_tcp_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpClient {
	public final static int PORT = 1333;
	
	public static void main(String [] args) {
		System.out.println("You are connected to the server!");
		System.out.println("For logging in type only ypur username NOTHING ELSE");
		System.out.println("For logging out type 'LOGOUT->'");
		System.out.println("For private message type 'PRIVATE-TheirUsername:yourMessage'");
		System.out.println("For list of clients type 'LIST->'");
		System.out.println("For public message type 'PUBLIC-yourMessage'");
		try {
			Socket socket = new Socket("localhost", PORT);
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
			Client client = new Client(in.readLine());
			oos.writeObject(client);
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message m;
					while(socket.isConnected()) {
						try {
							m = (Message) ois.readObject();
							System.out.println(m.getMessage());
							
						}catch (IOException e) {
								e.getMessage();
						} catch (ClassNotFoundException e) {
							e.getMessage();
						}
					}
					
				}
			}	
			).start();
			String [] a = in.readLine().split("-");
			int type = 0;
			Message m;
			while(true) {
				if(a[0].equalsIgnoreCase("LOGOUT")) break;
				if(socket.isClosed()) break;
				
				if(a[0].equalsIgnoreCase("LIST")) {
					type = 2;
					m = new Message(type, a[1]);
					oos.writeObject(m);
					System.out.println("Sending: " + m.getMessage());
				}
				if(a[0].equalsIgnoreCase("PRIVATE")) {
					type = 3;
					m = new Message(type, a[1]);
					oos.writeObject(m);
					System.out.println("Sending: " + m.getMessage());
				}
				if(a[0].equalsIgnoreCase("PUBLIC")) {
					type = 4;
					m = new Message(type, a[1]);
					oos.writeObject(m);
					System.out.println("Sending: " + m.getMessage());
				}
				
				a = in.readLine().split("-");
			}
			socket.close();
			oos.close();
			ois.close();
			
		} catch (IOException e) {
			e.getMessage();
			
		}
	}

}
