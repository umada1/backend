package blogapplication.backend.controllers;

import java.util.HashSet;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blogapplication.backend.classes.RightDefinition;
import blogapplication.backend.classes.Rights;
import blogapplication.backend.classes.Users;
import blogapplication.backend.repositories.RightsRepository;
import blogapplication.backend.repositories.UserRepository;


@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api") // adds api in front of all addresses 

public class UserController {
	
	private final UserRepository repository;
	private final RightsRepository rightsrep;

	UserController(UserRepository repository, RightsRepository rightsrep) {
	    this.repository = repository;
	    this.rightsrep = rightsrep;
	}
	
	
	@PostMapping("/register")
	Users addUser(@RequestBody Users newUser) {
		
		if(newUser.getPassword().length()<8) {
			throw new RuntimeException("password is too short, and needs to be at least 9 symbols long");
		}
		
		String password = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
		newUser.setPassword(password);
		
		Set<Rights> rights = new HashSet<>();
		Rights rightAdmin = new Rights(RightDefinition.ROLE_ADMIN);
		Rights rightUser = new Rights(RightDefinition.ROLE_USER);
		
		if(repository.existsByUsername(newUser.getUsername())) {
			throw new RuntimeException("username is already in use");
		}
		
		if(newUser.getRights() == null) {
			rights.add(rightUser);
		}
		else {
			rights.add(rightAdmin);
		}
		newUser.setRights(rights);
		return repository.save(newUser);
	}
	
	@GetMapping("/users")
	  List<Users> allUsers() {
	    return repository.findAll();
	}
	
	@DeleteMapping("/deleteUser/{id}")
	  void deleteUser(@PathVariable Long id) {
	    repository.deleteById(id);
	}
}