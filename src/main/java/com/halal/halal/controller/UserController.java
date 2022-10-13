package com.halal.halal.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.halal.halal.domain.Role;
import com.halal.halal.domain.User;
import com.halal.halal.repository.RoleRepository;
import com.halal.halal.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	int from = 0;
	int total = 0;
	public static final int ROWS = 10;
	Long records = 0L;

	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	@GetMapping("/adduser")
	public String addUser(Model model,HttpSession session){
	
		if(!model.asMap().containsKey("user")){
			   model.addAttribute("user", new User());
		} 

		if(model.asMap().containsKey("result")){
				System.err.println("in result");
				model.addAttribute("org.springframework.validation.BindingResult.user",model.asMap().get("result"));
		}
		
		List<Role> roles =  (List<Role>) roleRepository.findAll();
		model.addAttribute("roles",roles);
	   
		return "settings/userAdd";
	} 
	
	
	@PostMapping("/adduser")
	public String adduser(@Valid@ModelAttribute("user")User user,BindingResult result,RedirectAttributes redirect) {
	
		 user.setPassword(encodePassword(user.getPassword()));
		
		 if (result.hasErrors()) 
		 {
			 redirect.addFlashAttribute("user",user);
			 redirect.addFlashAttribute("result",result);
			 redirect.addFlashAttribute("fail","Enter fields correctly");
			 return "redirect:/user/adduser";       
		 }
		 else if(userRepository.existsByUsername(user.getUsername())) {
				redirect.addFlashAttribute("user", user);
				redirect.addFlashAttribute("fail", "User with same username already exists");
				return "redirect:/user/adduser";
		 }
		 
		 userRepository.save(user);
		 redirect.addFlashAttribute("success", "User saved successfully");
		 return "redirect:/user/adduser"; 
	}

	@GetMapping("/viewuser")
	public String viewUsers(Model model,HttpSession session) {
		int page=0;
		User user=(User) session.getAttribute("user");
		System.out.println("user======"+user.getUsername());
		model.addAttribute("user", user);
		Pageable pageable = PageRequest.of(page, 10);
		Page<User> list = userRepository.findAll(pageable);
		records=(long) list.getSize();
		System.out.println("total========"+list.getTotalPages());
		model.addAttribute("userList", list.getContent());
		session.setAttribute("page", page);
		model.addAttribute("total", list.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("records", records);
	
		return "settings/viewUsers";
	}
	

	@GetMapping("/viewuser/getpage/pageno={page}")
	public String viewUsers(@PathVariable("page") int page,Model model , HttpSession session ){
		User user=(User) session.getAttribute("user");
		model.addAttribute("user", user);
		System.out.println("page=========="+page);
		Pageable pageable = PageRequest.of(page, 10);
		Page<User> list = userRepository.findAll(pageable);
		records=(long) list.getSize();
		model.addAttribute("userList", list.getContent());
		session.setAttribute("page", page);
		model.addAttribute("total", list.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("records", records);
		
		return "settings/viewUsers";
		
	}
	
	@RequestMapping(value="/delete/{id}")
	public String viewUser(@PathVariable("id") Long id,RedirectAttributes red){
		
		User user = userRepository.findById(id).get();
		
		for(int i=0;i<user.getRoles().size();i++) {
			user.getRoles().remove(i);			
		}
		try {
		userRepository.delete(user);
		red.addFlashAttribute("success","User Deleted Succesfully");
		}
		catch(Exception e) {
			red.addFlashAttribute("fail","User Cannot be Deleted");
		}
		return "redirect:/settings/viewuser";
	}
	
	
	public String encodePassword(String text){
			return new BCryptPasswordEncoder().encode(text);
	}
	
}
