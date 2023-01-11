package es.vir2al.apuestas.fwk.configurations.security;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import es.vir2al.apuestas.fwk.utils.constants.GeneralConstants;
import es.vir2al.apuestas.fwk.utils.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
    
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
    public boolean validateToken(String authToken) {    	

        try {
        	
            Jwts.parser().setSigningKey(SecurityConstants.LLAVE_SECRETA.getBytes(Charset.forName(GeneralConstants.UTF8_STRING))).parseClaimsJws(authToken);
            
            return true;
        
        } catch (SignatureException ex) {
        	System.err.println(ex);
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
    
    public String getInfo(String token,String info) {
    	
        Claims claims = Jwts.parser()
        		.setSigningKey(SecurityConstants.LLAVE_SECRETA.getBytes(Charset.forName(GeneralConstants.UTF8_STRING)))
                .parseClaimsJws(token)
                .getBody();

        return claims.get(info).toString();
    	
    }
	
}

