package project.demo.pd1;

import java.util.List;

public class SearchResultFormat {

	String success, message;
	List<SearchResultSmall> posts;

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

	public List<SearchResultSmall> getPost() {
		return posts;
	}

	public void setPost(List<SearchResultSmall> post) {
		this.posts = posts;
	}

		
}
