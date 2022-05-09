package blogapplication.backend.tokenAuth;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import blogapplication.backend.classes.Users;
import blogapplication.backend.repositories.UserRepository;

@Service
public class DetailsOfUsers implements UserDetailsService{

	@Autowired 
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users i = repository.findByUsername(username);
		
		return SelectedUser.select(i);
		
	}


	
}
