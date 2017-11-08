package com.bridgeit.toDoNotes.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.toDoNotes.model.User;
import com.bridgeit.toDoNotes.services.UserServiceImpl;

@RestController
public class UserLogIn {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> logInUser(@RequestBody User userDetails,HttpSession session) {
		System.out.println("inside Login");
		User user=userServiceImpl.getUser(userDetails.getEmail(),userDetails.getPassword());
	
			
			if(user!=null) {
				if(user.isActivated()) {
					/*HttpSession session=((HttpServletRequest) request).getSession();*/
/*					System.out.println("####### Usre id is: "+user.getUserId()+" user name is "+user.getEmail());
*/					session.setAttribute("Name", user);

					return  ResponseEntity.status(HttpStatus.CREATED).body("User log in succesfully");
				} else {
					
					return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user is not verified please click on registration link ") ;
				}
				

			} else {

				return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user email or password are wrong");

			}
		
	}
}

