package blogapplication.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import blogapplication.backend.filter.RequestFilter;
import blogapplication.backend.jwtHandling.Utilities;
import blogapplication.backend.repositories.ResourceRepository;
import blogapplication.backend.repositories.UserRepository;
import blogapplication.backend.tokenAuth.DetailsOfUsers;
import blogapplication.backend.tokenAuth.SelectedUser;

@WebMvcTest(RequestFilter.class)
public class RequestFilterTest {
	
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
	
	
	// if header is null 
	@Test
	public void headerIsNull() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		
		Mockito.when(request.getHeader("Authorization")).thenReturn(null);
		
		mvc.perform(MockMvcRequestBuilders.get("/api/users")).andExpect(status().isForbidden());
	}
	// if header is not null
	
	@Test
	@WithMockUser
	public void headerIsNotNull() throws Exception {
		Utilities utility = new Utilities();
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		SelectedUser user = new SelectedUser((long) 1, "Username", "Password", null);
		String jwt = utility.newJwt(user);
		Mockito.when(request.getHeader("Authorization")).thenReturn(jwt);
		assertNotNull(request.getHeader("Authorization"));
		String header = request.getHeader("Authorization");
		String token = header.toString();
		String username = utility.getUserName(token);
		assertEquals("Username", username);
	}
	

}
