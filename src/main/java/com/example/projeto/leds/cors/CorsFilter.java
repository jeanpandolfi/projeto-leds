package com.example.projeto.leds.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.projeto.leds.config.property.ProjetoLedsProperty;

//@Component
//public class CorsFilter extends OncePerRequestFilter{
//	
//	@Autowired	
//	private ProjetoLedsProperty property;
//	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		
//		response.addHeader("Access-Control-Allow-Origin",property.getOrigemPermitida());
//		response.addHeader("Access-Control-Allow-Methods"," POST, GET, OPTIONS, DELETE, PUT");
//		response.addHeader("Access-Control-Allow-Credentials","true");
//		response.addHeader("Access-Control-Allow-Headers",
//							"content-type, x-gtw-module-base, x-gwt-permutation,clientid,longpush");
//		filterChain.doFilter(request, response);
//		
//	}
//	
//}


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	
	@Autowired	
	private ProjetoLedsProperty property; 
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		 
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		res.setHeader("Access-Control-Allow-Origin", property.getOrigemPermitida());
		res.setHeader("Access-Control-Allow-Credentials", "true");
		
		if("OPTIONS".equals(req.getMethod()) && 
				this.property.getOrigemPermitida().equals(req.getHeader("Origin"))) {
			
			res.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			res.setHeader("Access-Control-Max-Age", "3600");
 			
			res.setStatus(HttpServletResponse.SC_OK);
		}else {
			chain.doFilter(req, res);
		}
		
	}
	
}
