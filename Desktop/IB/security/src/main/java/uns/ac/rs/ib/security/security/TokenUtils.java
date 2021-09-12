package uns.ac.rs.ib.security.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {
	
	@Value("spring-security-example")
	private String APP_NAME;
	
	 @Value("myXAuthSecret")
	 private String SECRET;

	 @Value("30000")
	 private int EXPIRES_IN;
	 
	@Value("Authorization")
	private String AUTH_HEADER;
	
	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;


	  public String getUsernameFromToken(String token) {
	     String username;
	      try {
	            Claims claims = this.getClaimsFromToken(token);
	            username = claims.getSubject();
	        } catch (Exception e) {
	            username = null;
	        }
	        return username;
	    }

	    private Claims getClaimsFromToken(String token) {
	        Claims claims;
	        try {
	            claims = Jwts.parser().setSigningKey(this.SECRET).parseClaimsJws(token).getBody();
	        } catch (Exception e) {
	            claims = null;
	        }
	        return claims;
	    }

	    public Date getExpirationDateFromToken(String token) {
	        Date expiration;
	        try {
	            final Claims claims = this.getClaimsFromToken(token);
	            expiration = claims.getExpiration();
	        } catch (Exception e) {
	            expiration = null;
	        }
	        return expiration;
	    }

	    private boolean isTokenExpired(String token) {
	        final Date expiration = this.getExpirationDateFromToken(token);
	        return expiration.before(new Date(System.currentTimeMillis()));
	    }

	    public boolean validateToken(String token, UserDetails userDetails) {
	        final String username = getUsernameFromToken(token);
	        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	    }

	    
	    
	    private Date generateExpirationDate() {
			return new Date(new Date().getTime() + EXPIRES_IN);
		}
	    
		public int getExpiredIn() {
			return EXPIRES_IN;
		}
	    
	 // Funkcija za generisanje JWT token
		
		public String generateToken(String username) {
			return Jwts.builder()
					.setIssuer(APP_NAME)
					.setSubject(username)
					//.setAudience(generateAudience())
					.setIssuedAt(new Date())
					.setExpiration(generateExpirationDate())
					// .claim("key", value) 
					.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
		}
		
		
		//stara metoda 
	    /*
	    public String generateToken(UserDetails userDetails) {
	        Map<String, Object> claims = new HashMap<String, Object>();
	        claims.put("sub", userDetails.getUsername());
	        claims.put("created", new Date(System.currentTimeMillis()));
	        return Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() * 2))
	                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
	    }
*/
		
		

	
}
