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
	
	Resources(){
		
	}
	
	public Resources(long id, String entry) {
		this.id = id;
		this.entry = entry;
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
	
}
