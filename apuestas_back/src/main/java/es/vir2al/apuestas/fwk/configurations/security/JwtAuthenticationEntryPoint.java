package es.vir2al.apuestas.fwk.configurations.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {

		logger.error("Responding with unauthorized error. Message - {}", e.toString());
		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.toString());

	}

}
