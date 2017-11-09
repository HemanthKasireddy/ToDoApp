package com.bridgeit.toDoNotes.DAO;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.bridgeit.toDoNotes.model.User;

public class UserDAO {
	@Autowired
	private   SessionFactory sessionFactory;

	public long createUser(User user) {

		/*if(sessionFactory==null) {
			System.out.println("connection incorrect");
		} else {
			System.out.println("connection correct");
		}*/
		Session session=sessionFactory.openSession();
		System.out.println("session is opened ");
		session.beginTransaction();
		System.out.println("transaction is opened ");
		String encryptPassword=BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(encryptPassword);
		long id=(Long) session.save(user);
		session.getTransaction().commit();
		System.out.println("inserting to database ");

		session.close();
		return id;
	}

	public User getUserById(long id) {
		if(sessionFactory==null) {
			System.out.println("connection incorrect");
		} else {
			System.out.println("connection correct");
		}
		Session session=sessionFactory.openSession();
		System.out.println("session is opened ");
		session.beginTransaction();
		System.out.println("transaction is opened ");

		User user=session.get(User.class, id);
		session.getTransaction().commit();
		System.out.println(" up dated inserting to database ");

		session.close();
		return user;
	}

	@SuppressWarnings("rawtypes")
	public int updateUser(User user) {

		if(sessionFactory==null) {
			System.out.println("connection incorrect");
		} else {
			System.out.println("connection correct");
		}
		Session session=sessionFactory.openSession();
		System.out.println("session is opened ");
		session.beginTransaction();
		System.out.println("transaction is opened ");
		Query query = session.createQuery("update User set activated=:activated where id=:id");
		//User user=session.load(User.class, new Long(id));
		user.setActivated(true);

		query.setParameter("activated", user.isActivated());
		query.setParameter("id",user.getUserId());

		int numberOfRows=query.executeUpdate();

		session.getTransaction().commit();
		System.out.println("inserting to database ");
		session.close();
		System.out.println("numberOfRows"+numberOfRows);
		return numberOfRows;
	}

	@SuppressWarnings("rawtypes")
	public User getUser(String email, String password) {

		if(sessionFactory==null) {
			System.out.println("connection incorrect");
		} else {
			System.out.println("connection correct");
		}
		Session session=sessionFactory.openSession();
		System.out.println("session is opened ");
		session.beginTransaction();
		System.out.println("transaction is opened ");
		Query query=session.createQuery("from User where UserEmail=:UserEmail");
		query.setParameter("UserEmail",email);
		//logger.debug(password);
		//query.setParameter("password",BCrypt.password);

		User user1=(User) query.uniqueResult();

		session.getTransaction().commit();
		session.close();
		if(BCrypt.checkpw(password,user1.getPassword())) {
			return user1;
		} else {
			return null;
		}

	}

	public int updateUserPassword(String password, String email) {
		Session session=sessionFactory.openSession();
		System.out.println("session is opened ");
		session.beginTransaction();
		System.out.println("transaction is opened ");
		System.out.println("user new password is "+password);
		String encryptPassword=BCrypt.hashpw(password, BCrypt.gensalt());
		//user.setPassword(encryptPassword);
		Query query = session.createQuery("update User set UserPassword=:UserPassword where UserEmail=:UserEmail");
		query.setParameter("UserPassword", encryptPassword);
		query.setParameter("UserEmail", email);
		int numberOfRows=query.executeUpdate();
		session.getTransaction().commit();
		System.out.println("inserting to database ");
		session.close();

		return numberOfRows;
	}

	public User getUserByEmail(String email) {
		Session session=sessionFactory.openSession();
		System.out.println("session is opened ");
		session.beginTransaction();
		System.out.println("transaction is opened ");

		Query query=session.createQuery("from User where UserEmail=:UserEmail");
		query.setParameter("UserEmail",email);
		//logger.debug(password);
		//query.setParameter("password",BCrypt.password);

		User user=(User) query.uniqueResult();
		session.getTransaction().commit();
		System.out.println(" up dated inserting to database ");

		session.close();
		return user;
		}


}
