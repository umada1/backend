package blogapplication.backend.repositories;


import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import blogapplication.backend.classes.Users;


public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByUsername(String username);
}