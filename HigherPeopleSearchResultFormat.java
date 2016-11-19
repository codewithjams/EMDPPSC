package project.demo.pd1;

import java.util.List;

public class HigherPeopleSearchResultFormat {
		
	String success, message;
	List<SmallHigherPeople> posts;
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
	public List<SmallHigherPeople> getPosts() {
		return posts;
	}
	public void setPosts(List<SmallHigherPeople> posts) {
		this.posts = posts;
	}
	
	
	
}
