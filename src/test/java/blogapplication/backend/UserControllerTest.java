package blogapplication.backend;
import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import blogapplication.backend.classes.Users;
import blogapplication.backend.controllers.UserController;
import blogapplication.backend.jwtHandling.Utilities;
import blogapplication.backend.repositories.RightsRepository;
import blogapplication.backend.repositories.UserRepository;
import blogapplication.backend.tokenAuth.DetailsOfUsers;
import blogapplication.backend.tokenAuth.SelectedUser;
@WebMvcTest(UserController.class)

public class UserControllerTest {
	

	@Autowired
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
	
	// no authorization 
	
	// /api/register post// 
	
	@Test
	public void addUserTest() throws Exception{
		
		Users user = new Users();
		user.setId((long) 1);
		user.setPassword("longpassword");
		user.setRights(null);
		user.setUsername("test@gmail.com");
		
		Mockito.when(repository.save(user)).thenReturn(user);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void addUserAdminTest() throws Exception{
		
		Users user = new Users();
		user.setId((long) 1);
		user.setPassword("longpassword");
		user.setUsername("test@gmail.com");
		
		Mockito.when(repository.save(user)).thenReturn(user);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
		.andExpect(status().isOk());
		
	}
	
	// password limitation
	
	@Test
	public void addUserShortPasswordTest() throws Exception{
		
		Users user = new Users();
		user.setId((long) 1);
		user.setPassword("short");
		user.setRights(null);
		user.setUsername("test@gmail.com");
		
		NestedServletException error = Assertions.assertThrows(NestedServletException.class, 
			() -> mvc.perform(MockMvcRequestBuilders.post("/api/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(user))));

		assertTrue(error.getMessage().contains("password is too short, and needs to be at least 8 symbols long"));
		
	}
	
	
	// the same username when one already exists
	
	@Test
	public void addUserDuplicateTest() throws Exception{
		
		Users user = new Users();
		user.setId((long) 1);
		user.setPassword("longpassword");
		user.setRights(null);
		user.setUsername("test@gmail.com");
		
		Mockito.when(repository.existsByUsername(user.getUsername())).thenReturn(true);
		
		NestedServletException error = Assertions.assertThrows(NestedServletException.class, 
				() -> mvc.perform(MockMvcRequestBuilders.post("/api/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(user))));
		assertTrue(error.getMessage().contains("username is already in use"));
		
	}
	
	@Test
	public void addUserNoDuplicateTest() throws Exception{
		
		Users user = new Users();
		user.setId((long) 1);
		user.setPassword("longpassword");
		user.setRights(null);
		user.setUsername("test@gmail.com");
		
		Mockito.when(repository.existsByUsername(user.getUsername())).thenReturn(false);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(user)))
		.andExpect(status().isOk());
		
	}
	
	// api/users get
	
	@Test
	public void getUsersTestWhithoutAuth() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/api/users")).andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(username = "test@gmail.com", authorities = { "ROLE_USER", "ROLE_ADMIN"})
	public void getUsersTestWithAuth() throws Exception{
		
		Users user = new Users();
		user.setId((long) 1);
		user.setPassword("longpassword");
		user.setRights(null);
		user.setUsername("test@gmail.com");
		
		List<Users> allUsers = new ArrayList<Users>();
		allUsers.add(0, user);
		
		Mockito.when(repository.findAll()).thenReturn(allUsers);
		
		mvc.perform(MockMvcRequestBuilders.get("/api/users")).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].username", Matchers.is("test@gmail.com")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].password", Matchers.is("longpassword")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].rights", Matchers.is(nullValue())));
		
	}
	
	// api/deleteUser/80 delete
	
	@Test
	public void deleteUserTestWithoutAuth() throws Exception{
		mvc.perform(MockMvcRequestBuilders.delete("/api/deleteUser/80"))
		.andExpect(status().isForbidden());
	}
	
	@Test 
	@WithMockUser(username = "test@gmail.com", authorities = { "ROLE_USER"})
	public void deleteUserTestUser() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/api/deleteUser/80"))
		.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(username = "test@gmail.com", authorities = {"ROLE_ADMIN"})
	public void deleteUserTestAdmin() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/api/deleteUser/80"))
		.andExpect(status().isOk());
	}
	

}
