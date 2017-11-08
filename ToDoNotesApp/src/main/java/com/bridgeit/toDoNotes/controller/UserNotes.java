package com.bridgeit.toDoNotes.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.toDoNotes.model.Notes;
import com.bridgeit.toDoNotes.model.User;
import com.bridgeit.toDoNotes.services.INotesService;

@RestController
/*@RequestMapping(value = "/notes")*/
public class UserNotes {
	@Autowired
	private INotesService iNotesService;
	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public ResponseEntity<String> createNote(@RequestBody Notes notes,HttpSession session) {
		notes.setCreatedTime(new Date());
		notes.setUpdatedTime(notes.getCreatedTime());
		User user=(User) session.getAttribute("Name");
		System.out.println(user);
		System.out.println("&&&&&&&&&&&"+session.getAttribute("Name"));
		System.out.println("####### Usre id is: "+user.getUserId()+" user name is "+user.getEmail());

		notes.setUser(user);
		long id=iNotesService.createNote(notes);
		if(id>0) {


			return  ResponseEntity.status(HttpStatus.CREATED).body("Note created succesfully");

		} else {

			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");

		}
	}
	@RequestMapping(value="/getAllNotes",method=RequestMethod.GET)
	public ResponseEntity<List<Notes>> getAllNotes(HttpSession session) {
		User user=(User) session.getAttribute("Name");

		List<Notes> notes =iNotesService.getAllNotes(user.getUserId());
		if(notes!=null) {


			System.out.println("Notes are "+notes);

			return  ResponseEntity.ok(notes);

		} else {

			return  null;

		}
	}
	@RequestMapping(value="/getNodeById/{id}",method=RequestMethod.GET)
	public ResponseEntity<List<Notes>> getNoteById(@PathVariable("id") long noteId,HttpSession session) {
		User user=(User) session.getAttribute("Name");

		List<Notes> notes =iNotesService.getNoteById(user.getUserId(),noteId);
		if(notes!=null) {


			System.out.println("Notes are "+notes);

			return  ResponseEntity.ok(notes);

		} else {
			
			return  null;

		}
	}
	@RequestMapping(value="/deleteNote/{id}",method = RequestMethod.POST)
	public ResponseEntity<String> deleteNote(@PathVariable("id") long id,HttpSession session) {
		User user=(User) session.getAttribute("Name");
		long responseCount =iNotesService.deleteNote(user.getUserId(),id);
		if(responseCount>0) {
			return  ResponseEntity.status(HttpStatus.ACCEPTED).body("Note deleted succesfully");
		} else {
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Note deleted unsuccesfully");

		}
	}
	@RequestMapping(value="/updateNote/{id}", method= RequestMethod.POST)
	public ResponseEntity<String> updateNote(@RequestBody Notes notes,@PathVariable("id") long id,HttpSession session) {
		User user=(User) session.getAttribute("Name");
		long responseCount =iNotesService.updateNote(notes,user.getUserId(),id);
		if(responseCount>0) {
			return  ResponseEntity.status(HttpStatus.ACCEPTED).body("Note updated succesfully");
		} else {
			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Note updated unsuccesfully");
		}
	}
}
