package com.bridgeit.toDoNotes.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.toDoNotes.model.User;
import com.bridgeit.toDoNotes.services.IMailerService;
import com.bridgeit.toDoNotes.services.UserServiceImpl;
import com.bridgeit.toDoNotes.validations.UserRegistrationValidations;

@RestController
public class UserRegistration {

	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private IMailerService iMailerService;

	@Autowired
	private UserRegistrationValidations userRegistrationValidations;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> registeringUser(@RequestBody User user,HttpServletRequest request) {
		if(userRegistrationValidations.validDetails(user)) {
			long id=userServiceImpl.insertUser(user);
			if(id>0) {

				String url=String.valueOf(request.getRequestURL());
				url=url.substring(0, url.lastIndexOf("/"))+"/activate/"+id;
				System.out.println(url);
				user.setActivated(false);
				iMailerService.sendMail(user.getEmail(),url);

				return  ResponseEntity.status(HttpStatus.CREATED).body("User registered succesfully");

			} else {

				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");

			}
		} else {
			return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("validations fails");
		}
	}

	/*  @RequestMapping(value = "/register", method = RequestMethod.POST)
	  public ResponseEntity<User> getUser(@RequestBody String email) {
		boolean check=userServiceImpl.insertUser(user);
		if(check) {
			return new ResponseEntity<User>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);

		}
	}  */
	@RequestMapping(value = "/activate/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> activateUser(@PathVariable("id") int id){

		User user=userServiceImpl.getUserById(id);
		if(user!=null){

			if(userServiceImpl.updateUser(user)){

				return ResponseEntity.ok("User Activated");

			} else {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in activation");
			}
		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Does not Exist");
		}
	}

}
