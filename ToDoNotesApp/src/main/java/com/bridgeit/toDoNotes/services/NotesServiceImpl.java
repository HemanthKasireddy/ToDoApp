package com.bridgeit.toDoNotes.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.bridgeit.toDoNotes.DAO.INotesDAO;
import com.bridgeit.toDoNotes.model.Notes;

public class NotesServiceImpl implements INotesService {

	@Autowired
	private INotesDAO iNotesDAO;

	@Override
	public long createNote(Notes notes) {
		long id=iNotesDAO.createNote(notes);
		return id;
	}
	@Override
	public List<Notes> getAllNotes(long userId) {
				List<Notes> notes= iNotesDAO.getAllNotes(userId);
				
					System.out.println("Notes are "+notes);
				
				return notes;
		
	}


	@Override
	public long deleteNote(long userId, long id) {
		long responseCount=iNotesDAO.deleteNote(userId,id);
		
		return responseCount;
	}


	@Override
	public List<Notes> getNoteById(long userId, long noteId) {
		return iNotesDAO.getNoteById(userId,noteId);
		
	}

	@Override
	public int updateNote(Notes notes, long userId, long noteId) {
		int responseCount=iNotesDAO.updateNote(notes,userId,noteId);
		return responseCount;
	}


}
