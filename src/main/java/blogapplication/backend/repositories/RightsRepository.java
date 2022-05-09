package blogapplication.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import blogapplication.backend.classes.RightDefinition;
import blogapplication.backend.classes.Rights;

public interface RightsRepository extends JpaRepository<Rights, Long>{
	
	Rights findByAccess(RightDefinition access);
	
}
