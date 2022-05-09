package blogapplication.backend.classes;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity

public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable
	private Set<Rights> rights = new HashSet<>();
	
	public Users(){
		
	}
	
	public Users(long id, String username, String password, Set<Rights> rights) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.rights = rights;
	}
	
	public long getId() {
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Rights> getRights() {
		return rights;
	}
	
	public void setRights(Set<Rights> rights) {
		this.rights = rights;
	}

}
