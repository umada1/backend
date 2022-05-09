package blogapplication.backend;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import blogapplication.backend.classes.Resources;
import blogapplication.backend.controllers.ResourceController;
import blogapplication.backend.jwtHandling.Utilities;
import blogapplication.backend.repositories.ResourceRepository;
import blogapplication.backend.tokenAuth.DetailsOfUsers;
import blogapplication.backend.tokenAuth.SelectedUser;
@WebMvcTest(ResourceController.class)
public class ResourceControllerTest {

	@Autowired
	private ResourceController controller;
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ResourceRepository repository;
	
	@MockBean
	private SelectedUser selected;
	
	@MockBean 
	private Utilities utilities;
	
	@MockBean 
	private DetailsOfUsers details;
	
	// get all resources (allResourcesTest)
	
	// without being authenticated
	@Test
	public void allResourcesTestWithoutAuth() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.get("/api/resources")).andExpect(status().isForbidden());
	}
	
	@Test
	public void addResourceTestWithoutAuth() throws Exception {
		
		Resources res = new Resources();
		res.setId((long) 1);
		res.setAuthor("test@gmail.com");
		res.setCreation("2022-05-03 12:30");
		res.setEntry("test resource");
		
		mvc.perform(MockMvcRequestBuilders.post("/api/addResource")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(res)))
		.andExpect(status().isForbidden());
	}
	
	@Test public void deleteUserWithoutAuth() throws Exception{
		
		mvc.perform(MockMvcRequestBuilders.delete("/api/deleteResource/18"))
		.andExpect(status().isForbidden());
	}
	
	// being authenticated
	
	@Test
	@WithMockUser(username = "test@gmail.com", authorities = { "ROLE_USER", "ROLE_ADMIN"})
	public void allResourcesWithAuth() throws Exception {
		
		Resources res = new Resources();
		res.setId((long) 1);
		res.setAuthor("test@gmail.com");
		res.setCreation("2022-05-03 12:30");
		res.setEntry("test resource");
		
		List<Resources> allResources = new ArrayList<Resources>();
		allResources.add(0, res);
		
		Mockito.when(repository.findAll()).thenReturn(allResources);
		
		mvc.perform(MockMvcRequestBuilders.get("/api/resources")).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].author", Matchers.is("test@gmail.com")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].entry", Matchers.is("test resource")))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].creation", Matchers.is("2022-05-03 12:30")));
	}
	
	@Test
	@WithMockUser(username = "test@gmail.com", authorities = { "ROLE_USER", "ROLE_ADMIN"})
	public void addResourceTestWithAuth() throws Exception {
		
		Resources res = new Resources();
		
		res.setId((long) 1);
		res.setAuthor("test@gmail.com");
		res.setCreation("2022-05-03 12:30");
		res.setEntry("test resource");
		
		Mockito.when(repository.save(res)).thenReturn(res);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/addResource")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(res)))
		.andExpect(status().isOk());
	}
	
	// delete resource (deleteResourceTest)
	
	// for users
	
	@Test 
	@WithMockUser(username = "test@gmail.com", authorities = { "ROLE_USER"})
	public void deleteUserWithUserAccess() throws Exception{
		
		mvc.perform(MockMvcRequestBuilders.delete("/api/deleteResource/18"))
		.andExpect(status().isForbidden());
	}
	
	@Test 
	@WithMockUser(username = "test@gmail.com", authorities = {"ROLE_ADMIN"})
	public void deleteUserWithAdminAccess() throws Exception{
		mvc.perform(MockMvcRequestBuilders.delete("/api/deleteResource/18"))
		.andExpect(status().isOk());
	}
	
}
