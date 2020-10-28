package com.swe.assignment.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.swe.assignment.bean.StudentBean;
import com.swe.assignment.dao.StudentDAO;

/**
 * Singleton Class to hold the database connection creation logic
 * 
 * @author ambily
 *
 */
public class StudentDAOImpl {
	// variable to hold the singleton database instance
	private static StudentDAOImpl instance = null;
	// variable to hold the entityManager object
	private EntityManager entityManager;
	// variable to hold the entityManager factory object
	private EntityManagerFactory factory;

	// setting the entityManager and connection during the object creation
	private StudentDAOImpl() {
		setEntityManager();
	}

	private void setEntityManager() {
		// read the connection details from the persistence.xml file and create the
		// factory
		this.factory = Persistence.createEntityManagerFactory("StudentDB");
		// create the entity manager object
		this.entityManager = factory.createEntityManager();
	}

	/**
	 * creating a singleton instance of database connection class
	 * 
	 * @return
	 */
	public static synchronized StudentDAOImpl getInstance() {
		// if singleton instance is not available create an instance object
		if (null == instance) {
			instance = new StudentDAOImpl();
		}
		// else return the existing instance object
		return instance;
	}

	/**
	 * method to close the database connection
	 */
	public void closeConnection() {
		entityManager.close();
		factory.close();
	}

	/**
	 * Method to save the student bean into the database
	 * 
	 * @param studentBean
	 * @throws Exception if the student cannot be saved
	 */
	public void saveToDatabase(StudentBean studentBean) throws Exception {
		try {
			// begin a transaction
			entityManager.getTransaction().begin();
			// save the studentDAO object
			entityManager.persist(new StudentDAO(studentBean));
			// commit the transaction
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			// if any exception is thrown like already student existing rethrow the
			// exception after printing
			System.out.println(ex.getMessage());
			try {
				entityManager.getTransaction().rollback();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			throw ex;
		}

	}

	/**
	 * Method to query the database and retrieve all the student IDs
	 * 
	 * @return List of student IDs
	 * @throws Exception if the student IDs cannot be retrieved
	 */
	public List<Object> readStudentIds() throws Exception {
		// create a query to select all the student ids
		Query qry = entityManager.createQuery("select std.id from StudentDAO std");
		List<Object> studIDList = qry.getResultList();
		return studIDList;
	}

	/**
	 * Method to query the database based on the id passed to it and return the
	 * corresponding student object
	 * 
	 * @param id of the student
	 * @return student object
	 * @throws Exception if the student cannot be retrieved
	 */
	public StudentBean readStudent(int id) throws Exception {
		// create a query to select the student based on the id
		Query qry = entityManager.createQuery("select std from StudentDAO std where id=" + id);
		// get the single result of the query execution and store in object
		Object stuDao = qry.getSingleResult();
		// cast the object to StudentDAO and access the convert function to create the
		// equivalent studentBean object
		StudentBean student = ((StudentDAO) stuDao).convert();
		// return the student bean
		return student;
	}

	public int deleteStudent(int id) throws Exception {
		entityManager.getTransaction().begin();
		// create a query to select the student based on the id
		int deleteCount = entityManager.createQuery("delete from StudentDAO std where id=" + id).executeUpdate();

		entityManager.getTransaction().commit();
		return deleteCount;
	}

}
