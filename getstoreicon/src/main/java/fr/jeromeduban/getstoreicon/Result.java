package fr.jeromeduban.getstoreicon;


public class Result {
	
	TaskCompleteBitmap callback;
	String response;
	
	public Result(TaskCompleteBitmap callback, String response) {
		super();
		this.callback = callback;
		this.response = response;
	}

	public TaskCompleteBitmap getCallback() {
		return callback;
	}

	public void setCallback(TaskCompleteBitmap callback) {
		this.callback = callback;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
