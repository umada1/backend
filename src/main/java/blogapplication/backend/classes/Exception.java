package blogapplication.backend.classes;

public class Exception extends RuntimeException{
	public Exception() {
	    super ("No such user exists");
	  }
}
