package blogapplication.backend.jwtHandling;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class Utilities {
	private String SECRET_KEY = "myspecialkey";
	
	public Claims getFullClaims(String jwt) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
	}
	public <T> T getClaim(String jwt, Function<Claims, T> clearClaims) {
		final Claims claim = getFullClaims(jwt);
		return clearClaims.apply(claim);
	}
	
	public Date getValidityCredentials(String jwt) {
		return(getClaim(jwt,Claims::getExpiration));
	}
	
	public Boolean determineValidity(String jwt) {
		return getValidityCredentials(jwt).before(new Date());
	}
	
	public String getUserName(String jwt) {
		return getClaim(jwt, Claims::getSubject);
	}
	
	public String createJwt(Map<String,Object> clm, String selected) {
		return Jwts.builder().setClaims(clm).setSubject(selected).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 60*60*1000))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	public String newJwt(UserDetails user) {
		Map<String, Object> clm = new HashMap<>();
		return createJwt(clm, user.getUsername());
		
	}
	
	public Boolean confirmJwt(String jwt, UserDetails details) {
		final String userName = getUserName(jwt);
		return (userName.equals(details.getUsername()) && !determineValidity(jwt));
	}

}
