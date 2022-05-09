package blogapplication.backend;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import blogapplication.backend.jwtHandling.Utilities;
import blogapplication.backend.repositories.ResourceRepository;
import blogapplication.backend.repositories.UserRepository;
import blogapplication.backend.tokenAuth.DetailsOfUsers;
import blogapplication.backend.tokenAuth.SelectedUser;

@WebMvcTest(BlogAppApplication.class)
class BackendApplicationTests {

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
	@Autowired
	private MockMvc mvc;

	@Test
	@WithMockUser
	public void renders() throws Exception {};
	
}
