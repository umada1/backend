package blogapplication.backend.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blogapplication.backend.classes.Users;
import blogapplication.backend.repositories.UserRepository;


@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api") // adds api in front of all addresses 

public class UserController {
	
	private final UserRepository repository;

	UserController(UserRepository repository) {
	    this.repository = repository;
	}
	
	@PostMapping("/register")
	Users addUser(@RequestBody Users newUser) {
		String password = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
		newUser.setPassword(password);
		return repository.save(newUser);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/users")
	  List<Users> allUsers() {
	    return repository.findAll();
	}
	
}