package blogapplication.backend;

import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import blogapplication.backend.classes.RightDefinition;
import blogapplication.backend.classes.Rights;
import blogapplication.backend.classes.Users;
import blogapplication.backend.controllers.UserController;
import blogapplication.backend.jwtHandling.Utilities;
import blogapplication.backend.repositories.RightsRepository;
import blogapplication.backend.repositories.UserRepository;
import blogapplication.backend.tokenAuth.DetailsOfUsers;
import blogapplication.backend.tokenAuth.SelectedUser;

@WebMvcTest(DetailsOfUsers.class)
public class DetailsOfUsersTest {

	@MockBean
	private UserController controller;
	@Autowired
	private MockMvc mvc;
	@MockBean
	private UserRepository repository;
	
	@MockBean
	private RightsRepository rightsRepository;
	
	@MockBean
	private SelectedUser selected;
	
	@MockBean 
	private Utilities utilities;
	
	@MockBean 
	private DetailsOfUsers details;
	
	@Test
	@WithMockUser(username = "user", authorities = {"ROLE_ADMIN"}, password="longpassword")
	public void loadUserTest() throws Exception {
		
		Users user = new Users();
		user.setId((long) 1);
		user.setPassword("longpassword");
		Rights rights = new Rights();
		rights.setId();
		rights.setAccess(RightDefinition.ROLE_ADMIN);
		user.setRights(Set.of(rights));
		user.setUsername("user");
		
		Mockito.when(repository.save(user)).thenReturn(user);
		
		Mockito.when(repository.findByUsername("user")).thenReturn(user);
		
		assertNotNull(SelectedUser.select(user));
		
	}
}
