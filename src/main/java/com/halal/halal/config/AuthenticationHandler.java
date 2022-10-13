package com.halal.halal.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler {

			
		private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		

		@Override 
		public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication authentication)
				throws IOException, ServletException {
			
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			boolean authFlag=false;
			for (GrantedAuthority authority : authorities) {
				if(authority.getAuthority().equals("ROLE_ADMIN")) {
					authFlag=true;
					try {
						redirectStrategy.sendRedirect(arg0, arg1, "/dashboard/home");
						break;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				  else if(authority.getAuthority().equals("ROLE_USER")) {
					  authFlag=true;
					try {
						redirectStrategy.sendRedirect(arg0, arg1, "/dashboard/home");
						break;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
			if(!authFlag)
				throw new IllegalStateException();
			authFlag=false;
				
			}
		}	
		


}
		
		
	


