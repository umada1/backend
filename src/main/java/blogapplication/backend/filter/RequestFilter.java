package blogapplication.backend.filter;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import blogapplication.backend.jwtHandling.Utilities;
import blogapplication.backend.tokenAuth.DetailsOfUsers;

@Component
public class RequestFilter extends OncePerRequestFilter{

	@Autowired
	private DetailsOfUsers details;
	@Autowired 
	private Utilities utility;
	// this is a generated method
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		
		String username = null;
		String token = null;
		
		if(header != null) {
			token = header.toString();
			username = utility.getUserName(token);
		}
		if (SecurityContextHolder.getContext().getAuthentication() == null && username!=null) {
			UserDetails udetails = this.details.loadUserByUsername(username);
			
			if (utility.confirmJwt(token, udetails)) {
				UsernamePasswordAuthenticationToken official = 
						new UsernamePasswordAuthenticationToken(udetails, null, null);
				official.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(official);
			}
		}
		
		filterChain.doFilter(request,response);
	}

}
