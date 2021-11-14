package final_tcp_chat;
import java.io.Serializable;

public class Message implements Serializable{
	static final long serialVersionUID = 1L;
	public static final int LOGOUT = 1, LIST = 2, PRIVATE = 3, PUBLIC = 4, RETURN = 0;
	private int type;
	private String message;
	
	public Message(int type, String message) {
		this.type = type;
		this.message = message;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
