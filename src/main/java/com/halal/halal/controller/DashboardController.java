package com.halal.halal.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.halal.halal.domain.User;
import com.halal.halal.repository.UserRepository;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	UserRepository userRepository;
	
	
	@GetMapping("/home")
	public String home(HttpSession session) {
		
		UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user=userRepository.findByUsername(userDetails.getUsername());
		System.out.println("username======="+user.getUsername());
		session.setAttribute("user", user);
		
			return"settings/HomePage";
	}
	
	@GetMapping("/index")
	public String index(HttpSession session) {
		
		UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user=userRepository.findByUsername(userDetails.getUsername());
		System.out.println("username======="+user.getUsername());
		session.setAttribute("user", user);
		
			return"settings/index";
	}
	
	@GetMapping("/about")
	public String aboutUs(HttpSession session) {
		System.out.println("aboutttttttt");
		UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user=userRepository.findByUsername(userDetails.getUsername());
		System.out.println("username======="+user.getUsername());
		session.setAttribute("user", user);
		
			return"settings/about";
	}
	
	@GetMapping("/form")
	public String form(HttpSession session) {
		System.out.println("formmmmmmmmm");
		UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user=userRepository.findByUsername(userDetails.getUsername());
		System.out.println("username======="+user.getUsername());
		session.setAttribute("user", user);
		
			return"settings/formApply";
	}
	


	@GetMapping("/checklistform")
	public String checklistForm(HttpSession session) {
		System.out.println(" checklist formmmmmmmmm");
		UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user=userRepository.findByUsername(userDetails.getUsername());
		System.out.println("username======="+user.getUsername());
		session.setAttribute("user", user);
		
			return"settings/auditChecklist";
	}
}
