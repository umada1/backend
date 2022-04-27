package blogapplication.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import blogapplication.backend.classes.Resources;


public interface ResourceRepository extends JpaRepository<Resources, Long> {
	
}