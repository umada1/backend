package blogapplication.backend.tokenAuth;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import blogapplication.backend.classes.Users;
import blogapplication.backend.repositories.UserRepository;
import blogapplication.backend.tokenAuth.SelectedUser;

@Service
public class DetailsOfUsers implements UserDetailsService{

	@Autowired 
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users i = repository.findByUsername(username);
		
		if (i==null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new SelectedUser(i);
		
	}


	
}
