package blogapplication.backend.classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Resources {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String entry;
	
	private String author;
	
	private String creation;
	
	public Resources(){
		
	}
	
	public Resources(long id, String entry, String author, String creation) {
		this.id = id;
		this.entry = entry;
		this.author = author;
		this.creation = creation;
	}
	
	public long getId() {
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id;
	}
	
	public String getEntry() {
		return this.entry;
	}
	
	public void setEntry(String entry) {
		this.entry = entry;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getCreation() {
		return this.creation;
	}
	
	public void setCreation(String creation) {
		this.creation = creation;
	}
	
}
