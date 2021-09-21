package uns.ac.rs.ib.security.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uns.ac.rs.ib.security.model.User;
import uns.ac.rs.ib.security.repository.UserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenUtils {
	
	@Value("spring-security-example")
	private String APP_NAME;
	
	 @Value("myXAuthSecret")
	 private String SECRET;

	 @Value("300000")
	 private int EXPIRES_IN;
	 
	@Value("Authorization")
	private String AUTH_HEADER;

	@Autowired
	UserRepository userRepository;
	
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
		
		public String generateToken(String username) {
			User u = userRepository.findByEmail(username);
			System.out.println(u.getRoles().get(0).getAuthority() + " ROLEEEEE");
			return Jwts.builder()
					.setIssuer(APP_NAME)
					.setSubject(username)
					//.setAudience(generateAudience())
					.setIssuedAt(new Date())
					.setExpiration(generateExpirationDate())
					.claim("role", u.getRoles().get(0).getAuthority())
					// .claim("key", value) 
					.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
		}
		
		public String refreshToken(String token) {
			String refreshedToken;
			try {
				final Claims claims = this.getClaimsFromToken(token);
				claims.setIssuedAt(new Date());
				refreshedToken = Jwts.builder()
						.setClaims(claims)
						.setExpiration(generateExpirationDate())
						.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
			} catch (Exception e) {
				refreshedToken = null;
			}
			return refreshedToken;
		}

		public boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
			final Date created = this.getIssuedAtDateFromToken(token);
			return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
					&& (!(this.isTokenExpired(token)) ));
		}
		

		public Date getIssuedAtDateFromToken(String token) {
			Date issueAt;
			try {
				final Claims claims = this.getClaimsFromToken(token);
				issueAt = claims.getIssuedAt();
			} catch (Exception e) {
				issueAt = null;
			}
			return issueAt;
		}
	
		private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
			return (lastPasswordReset != null && created.before(lastPasswordReset));
		}
		
		public String getAuthHeaderFromHeader(HttpServletRequest request) {
			return request.getHeader(AUTH_HEADER);
		}
		
		public String getToken(HttpServletRequest request) {
			String authHeader = getAuthHeaderFromHeader(request);

			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				return authHeader.substring(7);
			}

			return null;
		}
		
		
}
