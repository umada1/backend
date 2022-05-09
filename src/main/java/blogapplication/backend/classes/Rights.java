package blogapplication.backend.classes;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rights {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private RightDefinition access;
	
	public Rights() {
		
	}
	public Rights(RightDefinition access) {
		this.access = access;
	}
	
	public Long getId() {
		return id;
	}
	public void setId() {
		this.id = id;
	}
	
	public RightDefinition getAccess() {
		return access;
	}
	
	public void setAccess(RightDefinition access) {
		this.access = access;
	}

}
