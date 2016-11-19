package project.demo.pd1;

import java.util.List;

public class NotificationsReplyFormat {

	String success, message;
	List<SmallNotification> notifs;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<SmallNotification> getNotifs() {
		return notifs;
	}
	public void setNotifs(List<SmallNotification> notifs) {
		this.notifs = notifs;
	}
	
	
		
}
