package br.com.ms.carrierservice.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtTokenFilter extends GenericFilterBean  {

	public static final String TOKEN = "authorization";

	private JwtTokenUtil jwtTokenUtil;

	
	public JwtTokenFilter(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	public void doFilter(ServletRequest requestServlet, ServletResponse responseServlet, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) requestServlet;
		HttpServletResponse response = (HttpServletResponse) responseServlet;
		final String xSfToken = request.getHeader(TOKEN);
        
        try {
        	if (xSfToken==null || StringUtils.isBlank(xSfToken) || !jwtTokenUtil.validateToken(xSfToken)) {
            	response.setStatus(HttpStatus.FORBIDDEN.value());
            	response.sendError(HttpStatus.FORBIDDEN.value(), "Token valid");
            	return;
            }
        }catch(ExpiredJwtException ex) {
        	if(!ex.getMessage().contains("JWT expired")) {
        		response.setStatus(HttpStatus.FORBIDDEN.value());
            	response.sendError(HttpStatus.FORBIDDEN.value(), "Token expired");
            	return;
        	}
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(xSfToken, xSfToken,Collections.singletonList(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);  
        chain.doFilter(request, response);
	}

}