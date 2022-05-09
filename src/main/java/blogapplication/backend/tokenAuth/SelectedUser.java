package blogapplication.backend.tokenAuth;

import java.util.Collection;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import blogapplication.backend.classes.Users;


public class SelectedUser implements UserDetails{
	
	private Long id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> rights;
	public SelectedUser(Long id, String username, String password,
			Collection<? extends GrantedAuthority> rights) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.rights = rights;
	}
	public static SelectedUser select(Users i) {
		
		List<GrantedAuthority> rights = i.getRights().stream()
				.map(right -> new SimpleGrantedAuthority(right.getAccess().toString()))
				.collect(Collectors.toList());
		return new SelectedUser(
				i.getId(), 
				i.getUsername(), 
				i.getPassword(), 
				rights);
	}
	
	
	private Users i;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return rights;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
