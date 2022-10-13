package com.halal.halal.config;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.halal.halal.domain.PasswordDTO;
import com.halal.halal.domain.User;
import com.halal.halal.repository.UserRepository;
import com.halal.halal.services.MailService;
import com.halal.halal.services.UserServiceInterface;

@Controller
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;
	@Autowired
	private UserRepository userService;
	
	@Autowired
	private UserServiceInterface userServiceInterface;
	
	@Autowired
	MailService mailService;
	

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
			@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
			public String login() {
		
			return "login";
				
			}

			@RequestMapping(value = "/api/authenticate", method = RequestMethod.POST)
			public @ResponseBody ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
				
				try {
					authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
				} catch (Exception e) {
					e.printStackTrace();
					     return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
				
				try {
					
						JwtResponse response = getResponse(authenticationRequest.getUsername(),
						authenticationRequest.getDeviceToken());
		
						/*
						 * CompletableFuture.runAsync(() ->
						 * sendNotifications(authenticationRequest.getDeviceToken(),
						 * authenticationRequest.getUsername()));
						 */
				         return ResponseEntity.ok(response);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

	
			private void authenticate(String username, String password) throws Exception {
				Objects.requireNonNull(username);
				Objects.requireNonNull(password);
		
				try {
					
					authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
				} catch (DisabledException e) {
					throw new Exception("USER_DISABLED", e);
				} catch (BadCredentialsException e) {
					throw new Exception("INVALID_CREDENTIALS", e);
				}
			}
			
		
			public JwtResponse getResponse(String username, String deviceToken) {
				final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(username);
		
				if (deviceToken != null) {
					System.out.println(" not null devicetokennnnn");
					userServiceInterface.updateDeviceToken(username, deviceToken);
				}
		
				final String token = jwtTokenUtil.generateToken(userDetails);
				JwtResponse response = new JwtResponse(token, userServiceInterface.getRolesOnUsername(username), username);
		
				return response;
			}
	
			
			@RequestMapping(value = "/api/logout", method = RequestMethod.POST)
			public ResponseEntity<?> logout(@RequestParam("deviceToken") String deviceToken) {
				if (!deviceToken.equals(null)) {
					userServiceInterface.logout(deviceToken);
				return ResponseEntity.ok(null);
				} else {
				return (ResponseEntity<?>) ResponseEntity.badRequest();
				}
		
			}
		
			//change password for mobile side
			@RequestMapping(value="/api/changePassword",method=RequestMethod.POST)
			public ResponseEntity<?> changePassword(@RequestBody PasswordDTO password){
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				System.err.println("username===="+password.getUserName());
				System.err.println("passord====="+password.getNewpassword());
				User user = userServiceInterface.findUserByUsername(password.getUserName());
			
			if(user== null) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}
				user.setPassword(passwordEncoder.encode(password.getNewpassword()));
				userServiceInterface.updateUser(user);
			
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}  
		
	        //password reset via email for mobile side
			@RequestMapping(value="/api/reset",method = RequestMethod.GET)
			public ResponseEntity<?> reset(HttpSession session,@RequestHeader("email") String email,HttpServletRequest request){
				System.out.println("email from param==="+email);
				if(userServiceInterface.findUserByEmail(email) == null)
				{
				return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
				}
				else {
					 User user=userServiceInterface.findUserByEmail(email);
					 user.setResetToken(UUID.randomUUID().toString());
					 user.setResetTokenExpiryTime(LocalDateTime.now().plusMinutes(5));
					 userServiceInterface.updateUser(user);
					 String emailId=user.getEmail();
					 String siteURL = siteUrl(request);
					 String resetPasswordLink = siteURL + "/api/reset_password?token=" + user.getResetToken();
					 System.out.println("site url==="+siteURL);
					 System.out.println("reset link-===="+resetPasswordLink);
					 System.out.println("time==="+user.getResetTokenExpiryTime());
					sendEmail(emailId,resetPasswordLink);
				
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);

				}
				
			}
			
			@RequestMapping(value="/api/reset_password",method = RequestMethod.GET)
			public ResponseEntity<?> resetPassword(HttpSession session,@RequestParam("token") String resetToken,HttpServletRequest request){
			
				    User user=userServiceInterface.findUserByResetToken(resetToken);
			if(user == null ) {
					
					System.err.println("user not found");
			return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);

				}
			else if(LocalDateTime.now().isAfter(user.getResetTokenExpiryTime())) {
				
					System.err.println("time out");
					user.setResetToken(null);
					user.setResetTokenExpiryTime(null);
					userServiceInterface.updateUser(user);
			return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);		
				}
				    System.out.println("success");
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);

				}
			
		
			public String siteUrl(HttpServletRequest request) {
				  String siteURL = request.getRequestURL().toString();
			return siteURL.replace(request.getServletPath(), "");
			        
			      
			}
			
			private boolean sendEmail(String email,String link) {
				String subject = "Here's the link to reset your password";
			    String message = "<p>Hello,</p>"
			            + "<p>You have requested to reset your password.</p>"
			            + "<p>Click the link below to change your password:</p>"
			            + "<p><a href=\"" + link + "\">Change my password</a></p>"
			            + "<br>"
			            +"<p><b>LINK IS VALID FOR 5 MINUTES<b></p>"
			            + "<p>Ignore this email if you do remember your password, "
			            + "or you have not made the request.</p>";
				mailService.sendEmailToAddress(message, email, subject);
			return true;
			}
}
