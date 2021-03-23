package com.sistema.delivery.api.core.security.authorizationserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

	@GetMapping("/login") 
	public String login()
	{
		return "/login"; 
	}
	
	@GetMapping("/oauth/confirm_access")
	public String approval()
	{
		return "/approval";
	}
	
}
