package es.vir2al.apuestas.fwk.configurations.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import es.vir2al.apuestas.fwk.domain.security.UserVO;
import es.vir2al.apuestas.fwk.utils.constants.SecurityConstants;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
    @Autowired
    private Environment environment;
	
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
    								HttpServletResponse response, 
    								FilterChain filterChain) throws ServletException, IOException {
    	
    	UserVO user;
    	String jwt = this.getJwtFromRequest(request);
    	
    	try {
    		
		 if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
	    		
					user = new UserVO(tokenProvider.getInfo(jwt,SecurityConstants.JWT_USERNAME),
														tokenProvider.getInfo(jwt,SecurityConstants.JWT_AUTHS),
														tokenProvider.getInfo(jwt,SecurityConstants.JWT_APLICACION)
					);

	    		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	    		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	    		
	    		SecurityContextHolder.getContext().setAuthentication(authentication);
	    		
	    	}
    	} catch (Exception ex) {
    		LOGGER.error("Could not set user authentication in security context", ex);
    	}
    	
    	filterChain.doFilter(request, response);
    	
    }
    
    private String getJwtFromRequest(HttpServletRequest request) {
        
    	String bearerToken = request.getHeader(SecurityConstants.HEADER_REQUEST_AUTH);
        
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.HEADER_REQUEST_BEARER)) {
            return bearerToken.substring(SecurityConstants.BEARER_LONG, bearerToken.length());
        } else {
        	
        	String testUser = request.getHeader(SecurityConstants.HEADER_REQUEST_TEST);
        	
        	if (testUser != null && !testUser.isEmpty() && testUser.contains("test")) {
        		
        		return testUser;
        		
        	}
        
        }
        
        return null;
    }
	
}

