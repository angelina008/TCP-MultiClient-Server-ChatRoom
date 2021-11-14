package final_tcp_chat;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadClass extends Thread{
	
	Socket socket;
	TcpServer server;
	private Client client;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public ThreadClass(Socket socket, TcpServer server) {
		this.socket = socket;
		this.server = server;
		try {
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			this.ois = new ObjectInputStream(socket.getInputStream());
			this.client = (Client)ois.readObject();
			System.out.println("Client " + client.getUsername() + " has joined!");
		}catch(Exception e) {
			e.getMessage();
		}
	}
	public Client getClient() {
		return client;
	}


	public void run() {
		try {
			while(true) {
				Message m = (Message) ois.readObject();
				Message respond;
				if(m.getType()==2) {
					String listOfClients = "List of connected clients: ";
					for(int i = 0; i < server.getList().size(); i++) {
						listOfClients+= server.getList().get(i).getClient().getUsername() + ", ";
					}
					respond = new Message(0, listOfClients);
					oos.writeObject(respond);
					
				}
				if(m.getType()==3) {
					String [] tmp = m.getMessage().split(":");
					String newMessage = client.getUsername() + ": " + tmp[1];
					Message n = new Message(0, newMessage);
					for(int i = 0; i < server.getList().size(); i++) {
						if(server.getList().get(i).getClient().getUsername().equalsIgnoreCase(tmp[0])) {
							server.getList().get(i).oos.writeObject(n);
							
						}
					}
					
				}
				if(m.getType()==4) {
					String tmp = m.getMessage();
					String newMessage = client.getUsername() + ": " + tmp;
					Message n = new Message(0, newMessage);
					//oos.writeObject(n);
					for(int i = 0; i < server.getList().size(); i++) {
						if(!server.getList().get(i).getClient().getUsername().equalsIgnoreCase(client.getUsername())) {
							server.getList().get(i).oos.writeObject(n);
							
						}
					}
				}
				
				m = (Message) ois.readObject();
			}
			
			
		}catch(Exception e) {
			e.getMessage();
		}
		
	}

}
