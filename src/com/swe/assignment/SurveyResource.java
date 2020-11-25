package com.swe.assignment;

import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.swe.assignment.bean.StudentBean;
import com.swe.assignment.dao.impl.StudentDAOImpl;

@Path("/survey")
public class SurveyResource {

	public SurveyResource() {

	}

	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudentsSurveyForm() {
		try {
			List<Object> obj = StudentDAOImpl.getInstance().readStudentIds();
			return Response.status(200).entity(obj).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Response.status(500).build();
	}*/
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudentsSurveyForm() {
		try {
			List<Object> obj = StudentDAOImpl.getInstance().readStudentIds();
			System.out.println(obj.get(0));
			return Response.status(200).entity(obj).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Response.status(500).build();
	}

	@DELETE
	@Path("{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStudentSurveyForm(@PathParam("studentId") String id) {
		try {
			//StudentDAOImpl.getInstance().deleteStudent(Integer.parseInt(id));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Response.status(500).build();
		}
		return Response.status(200).build();
	}

	@GET
	@Path("{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentSurveyForm(@PathParam("studentId") String id) {
		// initialize return studentBean to null
		StudentBean studentBean = null;
		try {
			// if queryString is available
			if (null != id) {
				// get the database instance object and read the student based on the
				// id(converting to int as we get it as string and in database we store as
				// integer)
				studentBean = StudentDAOImpl.getInstance().readStudent(id);
				return Response.status(200).entity(studentBean).build();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Response.status(500).build();
		}
		return Response.status(400).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createSurveyRecord(StudentBean studentBean) {
		try {
			System.out.println("inside post");
			StudentDAOImpl impl = new StudentDAOImpl();
			impl.saveToDatabase(studentBean);

			// save the student to the database
		/*	StudentDAOImpl impl = StudentDAOImpl.getInstance();
			// insert the student into the database, if any reason student insert fails it
			// will throw and exception
			String topicName = "qs";
			Properties props = new Properties();
		    
		    //Assign localhost id
		    props.put("bootstrap.servers", "localhost:9092");
		    
		    //Set acknowledgements for producer requests.      
		    props.put("acks", "all");
		    
		    //If the request fails, the producer can automatically retry,
		    props.put("retries", 0);
		    
		    //Specify buffer size in config
		    props.put("batch.size", 16384);
		    
		    //Reduce the no of requests less than 0   
		    props.put("linger.ms", 1);
		    
		    //The buffer.memory controls the total amount of memory available to the producer for buffering.   
		    props.put("buffer.memory", 33554432);
		    //props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	       // props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StudentBean.class.getName());
		    props.put("key.serializer", 
		       "org.apache.kafka.common.serialization.StringSerializer");
		    //props.put("value.serializer", "com.knoldus.serializers.UserSerializer"); 
		    props.put("value.serializer", 
		       "kafka.examples.common.serialization.UserSerializer");
		    
		    try (Producer<String, StudentBean> producer = new KafkaProducer<>(props)) {
		    	System.out.println("inside producercode");
		    	   producer.send(new ProducerRecord<String, StudentBean>("qs",studentBean));
		    	   System.out.println("Message " + studentBean.toString() + " sent !!");
		    	} catch (Exception e) {
		    	   e.printStackTrace();
		    	}
		             
			//impl.saveToDatabase(studentBean);*/
		} catch (Exception e) {
			// if the student insertion fails send to the home page with reason for the
			// error
			System.out.println("exception occurred during insertion " + e.getMessage());
			return Response.status(500).build();
		}
		return Response.status(200).entity(studentBean).build();
	}

}
