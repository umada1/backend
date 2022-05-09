package blogapplication.backend.controllers;


import java.util.List;



import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blogapplication.backend.classes.Resources;
import blogapplication.backend.repositories.ResourceRepository;


@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")

public class ResourceController {
	
	private final ResourceRepository repository;

	ResourceController(ResourceRepository repository) {
	    this.repository = repository;
	}
	
	@PostMapping("/addResource")
	Resources addResource(@RequestBody Resources newResource) {
	    return repository.save(newResource);
	}
	
	@GetMapping("/resources")
	  List<Resources> allResources() {
	    return repository.findAll();
	}
	
	@DeleteMapping("/deleteResource/{id}")
	  void deleteResource(@PathVariable Long id) {
	    repository.deleteById(id);
	}
	
}
