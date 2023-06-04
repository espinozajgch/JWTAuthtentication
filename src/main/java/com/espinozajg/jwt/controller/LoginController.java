package com.espinozajg.jwt.controller;

import com.espinozajg.jwt.dto.User;
import com.espinozajg.jwt.security.JWTAuthtenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@Autowired
	JWTAuthtenticationConfig jwtAuthtenticationConfig;

	@PostMapping("login")
	public User login(
			@RequestParam("user") String username,
			@RequestParam("password") String pass) {

		/**
		 * En el ejemplo no se realiza la correcta validación del usuario
		 */

		String token = jwtAuthtenticationConfig.getJWTToken(username);
		User user = new User();
		user.setUser(username);
		user.setPass(pass);
		user.setToken(token);		
		return user;
		
	}


}
