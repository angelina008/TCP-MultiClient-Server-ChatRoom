package final_tcp_chat;
import java.io.Serializable;

public class Client implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	public Client(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
