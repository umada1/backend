package blogapplication.backend;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import blogapplication.backend.classes.Request;
import blogapplication.backend.classes.Users;
import blogapplication.backend.controllers.AuthController;
import blogapplication.backend.jwtHandling.Utilities;
import blogapplication.backend.repositories.ResourceRepository;
import blogapplication.backend.repositories.UserRepository;
import blogapplication.backend.tokenAuth.DetailsOfUsers;
import blogapplication.backend.tokenAuth.SelectedUser;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
	
	@Autowired
	private AuthController controller;
	@Autowired
	private MockMvc mvc;
	@MockBean
	private UserRepository repository;
	@MockBean
	private ResourceRepository resRepository;
	@MockBean
	private SelectedUser selected;
	@MockBean 
	private Utilities utilities;
	@MockBean 
	private DetailsOfUsers details;
	@MockBean 
	private AuthenticationManager authMan;
	
	@Test 
	public void authUsernameNotFound() throws Exception{
		
		Request req = new Request();
		req.setUsername("test@gmail.com");
		req.setPassword("longpassword");
		
		Mockito.when(repository.findByUsername(req.getUsername())).thenReturn(null);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(req)))
		.andExpect(status().isNotFound())
		.andReturn();
	}
	
	@Test 
	public void authUsernameFound() throws Exception{
		
		Request req = new Request();
		req.setUsername("test@gmail.com");
		req.setPassword("longpassword");
		
		Users user = new Users();
		user.setId((long) 1);
		user.setPassword(BCrypt.hashpw("longpassword", BCrypt.gensalt()));
		user.setRights(null);
		user.setUsername("test@gmail.com");
		repository.save(user);
		
		Mockito.when(repository.findByUsername(req.getUsername())).thenReturn(user);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(req)))
		.andExpect(status().isOk())
		.andReturn();
	}
	
	
	@Test 
	public void authUsernameFoundMatchingCred() throws Exception{
		
		Request req = new Request();
		
		req.setUsername("test@gmail.com");
		req.setPassword("longpassword");
		
		Users user = new Users();
		user.setId((long) 1);
		user.setPassword(BCrypt.hashpw("longpassword", BCrypt.gensalt()));
		user.setRights(null);
		user.setUsername("test@gmail.com");
		
		repository.save(user);
		
		Mockito.when(repository.findByUsername(req.getUsername())).thenReturn(user);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(req)))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void authUsernameFoundNotMatchingCred() throws Exception{
		
		Request req = new Request();
		
		req.setUsername("test@gmail.com");
		req.setPassword("longpassword");
		
		Users user = new Users();
		user.setId((long) 1);
		user.setPassword(BCrypt.hashpw("shortpassword", BCrypt.gensalt()));
		user.setRights(null);
		user.setUsername("test@gmail.com");
		
		repository.save(user);
		
		Mockito.when(repository.findByUsername(req.getUsername())).thenReturn(user);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
		.andExpect(status().isNotFound());
		}
	
}
