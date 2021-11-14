package final_tcp_chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TcpServer {

	public final static int PORT = 1333;
	ArrayList<ThreadClass> list = new ArrayList<ThreadClass>();
	ServerSocket sSocket;
	
	public TcpServer() {
		System.out.println("Server is listening to port: "+ PORT);
		try {
			sSocket = new ServerSocket(PORT);
		}catch(IOException e){
			e.getMessage();
		}
	}
	public ArrayList<ThreadClass> getList(){
		return list;
	}
	public void StartServer() {
		try {
			while(true) {
				Socket socket = sSocket.accept();
				System.out.println("New Client is Connected! - WELCOME!");
				ThreadClass t = new ThreadClass(socket, this);
				t.start();
				list.add(t);
			}
			
		}catch(IOException e) {
			e.getMessage();
		}
		
	}
	
	
	public static void main(String [] args) {
		TcpServer tcpS = new TcpServer();
		tcpS.StartServer();
	}
	
}
