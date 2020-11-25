package com.swe.assignment.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.kafka.common.serialization.Serdes;

import com.swe.assignment.bean.StudentBean;
import com.swe.assignment.dao.StudentDAO;


import io.kcache.*;
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
	private String bootstrapServers = "localhost:9092";
	
	private Cache<String, StudentBean[]> cache;
	//StudentBean[] d =new StudentBean[1];

	// setting the entityManager and connection during the object creation
	public StudentDAOImpl() {
		//setEntityManager();
		Properties props = new Properties();
		props.put(KafkaCacheConfig.KAFKACACHE_BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(KafkaCacheConfig.KAFKACACHE_TOPIC_NUM_PARTITIONS_CONFIG, 3);
		props.put(KafkaCacheConfig.KAFKACACHE_TOPIC_REPLICATION_FACTOR_CONFIG, 1);
		props.put(KafkaCacheConfig.KAFKACACHE_TOPIC_CONFIG, "qe");
		props.put(KafkaCacheConfig.KAFKACACHE_TOPIC_REQUIRE_COMPACT_CONFIG, true);

		cache = new KafkaCache<>(new KafkaCacheConfig(props), Serdes.String(), Serdes.serdeFrom(new UserSerializer(), new UserDeserializer()));
		cache.init();
		//d[0]=new StudentBean("123","cde","cf","hj", "jk", 2,
			//	"45", "jkkk", "fgt",
				//"hjkk");
		//cache.put("qe",d);
	}

	
			
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
		System.out.println("insidesave");
		System.out.println(cache.get("qe"));

		StudentBean[] surveys = cache.get("qe");
		
		StudentBean[] nsurveys = new StudentBean[surveys.length+1];
		System.arraycopy(surveys, 0, nsurveys, 0, surveys.length);
		nsurveys[surveys.length] = studentBean;
		cache.put("qe", nsurveys);

	}

	/**
	 * Method to query the database and retrieve all the student IDs
	 * 
	 * @return List of student IDs
	 * @throws Exception if the student IDs cannot be retrieved
	 */
	public List<Object> readStudentIds() throws Exception {
		// create a query to select all the student ids
		System.out.println("read");
		//Query qry = entityManager.createQuery("select std.id from StudentDAO std");
		//List<Object> studIDList = qry.getResultList();
		//return studIDList;
		StudentBean[] surveys = cache.get("qe");
		List<Object> s1= new ArrayList<Object>();
		//disconnect();
		for(StudentBean s : surveys) {
			System.out.println(s.getId());
			s1.add(s.getId());
		}
		return s1;
	}
	
	
	public List<StudentBean> readStudentDB() throws Exception {
		List<StudentBean>s = new ArrayList<StudentBean>() ;
		// create a query to select all the student ids
		/*Query qry = entityManager.createQuery("select * from StudentDAO");
		@SuppressWarnings("unchecked")
		List<Object> studList = qry.getResultList();
		for (Object std: studList){
			s.add(((StudentDAO) std).convert());
		}*/
		return s;
	}

	/**
	 * Method to query the database based on the id passed to it and return the
	 * corresponding student object
	 * 
	 * @param id of the student
	 * @return student object
	 * @throws Exception if the student cannot be retrieved
	 */
	public StudentBean readStudent(String id) throws Exception {
		// create a query to select the student based on the id
		/*Query qry = entityManager.createQuery("select std from StudentDAO std where id=" + id);
		// get the single result of the query execution and store in object
		Object stuDao = qry.getSingleResult();
		// cast the object to StudentDAO and access the convert function to create the
		// equivalent studentBean object
		StudentBean student = ((StudentDAO) stuDao).convert();
		// return the student bean*/
		for(StudentBean s : cache.get("qe")) if(s.getId().equals(id)) return s;
		return null; 
		
	}

	public int deleteStudent(int id) throws Exception {
		entityManager.getTransaction().begin();
		// create a query to select the student based on the id
		int deleteCount = entityManager.createQuery("delete from StudentDAO std where id=" + id).executeUpdate();

		entityManager.getTransaction().commit();
		return deleteCount;
	}

}
