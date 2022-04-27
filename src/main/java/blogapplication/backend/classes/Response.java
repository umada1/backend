package blogapplication.backend.classes;

public class Response {
	
	private final String token;
	
	public Response(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	
}
