package com.moviebookingapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebookingapp.Auth.JwtUtils;
import com.moviebookingapp.Service.UserService;
import com.moviebookingapp.dto.ForgotPassword;
import com.moviebookingapp.exception.PasswordMismatchException;
import com.moviebookingapp.exception.UserAlredyExistException;
import com.moviebookingapp.exception.UserNotExistException;

//import com.moviebookingapp.kafka.Producer;
import com.moviebookingapp.models.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpHeaders;
@CrossOrigin( origins = {
        "http://localhost:3000",
        "https://moviebookingapp23.z29.web.core.windows.net"
    } , methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@Slf4j
@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtils jwtUtils;
//	
//	@Autowired
//	Producer producer;
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@Valid @RequestBody User user) throws UserAlredyExistException, PasswordMismatchException{
		
		log.info("register as new user");
		User newUser = userService.registerUser(user);
		//producer.send(newUser.getLoginId());
		log.info("New user registered");
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
		
		
	}
	@GetMapping("/login/{loginId}/{password}")
	public ResponseEntity<?> loginAuth(@PathVariable String loginId, @PathVariable String password){
		
		boolean result=userService.authenticate(loginId, password);
		if(result)
		{
			String token=jwtUtils.generateJwt(loginId);
			log.info("logged in successfully");
			//producer.send("token genrated "+token);
			
			 Map<String, Object> map = new HashMap<String, Object>();
			    map.put("token", token);
			 return new ResponseEntity<>(map,HttpStatus.OK);
			
		}
		
		
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
//	@GetMapping("/private")
//	public ResponseEntity<?> verify(@RequestHeader(value="authorization",defaultValue = "")String auth) throws Exception
//	{
//		jwtUtils.verify(auth);
//	String	result=" to verify";
//		
//		return new ResponseEntity<>(result,HttpStatus.OK);
//	}
//	@GetMapping("/login/{loginId}/{password}")
//	public ResponseEntity<?> login(@PathVariable String loginId, @PathVariable String password){
//		boolean result=userService.authenticate(loginId, password);
//		log.info("logged in successfully");
//		return new ResponseEntity<>(result,HttpStatus.OK);
//	}
	
	@PutMapping("/{loginId}/forgot")
	public ResponseEntity<String> forgotPassword(@PathVariable String loginId,@RequestBody ForgotPassword forgotPassword) throws PasswordMismatchException, UserNotExistException{
		userService.updatePassword(loginId, forgotPassword);
		//producer.send(loginId);
		
		log.info("password updated successfully");
		 return ResponseEntity.ok("Password updated successfully!!");
		// return ResponseEntity.ok().headers(responseHeaders).body("Password updated successfully!!");
		//return new ResponseEntity<String>("Password updated successfully!!",HttpStatus.CREATED);
	}

}
