package org.idchavan.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.idchavan.common.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {


	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@RequestMapping(value = "/home")
	public ModelAndView indexPage() {
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showWebpage(HttpServletRequest request, HttpServletResponse response) {
		
		//*********encode password*
		System.out.println("******* encoded pass " +bcrypt.encode("guest"));
		//*********
		ModelAndView mav = new ModelAndView("/");
		mav.addObject(new Login());
		return mav;
	}
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String doLogin(){
		return "login";
	}
	
	/*@RequestMapping(value = "/loginError", method = RequestMethod.GET)
	public String loginError(ModelMap model) {
		System.out.println("ERROR");
		model.addAttribute("error", "true");
		return "login";

	}*/
	@RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
	public ModelAndView loginProcess() {

		ModelAndView mav = new ModelAndView("login");
		mav.addObject("message", "Username or Password is wrong!!");

		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";// You can redirect wherever you want, but generally it's a good idea to show
							// login screen again.
	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}
