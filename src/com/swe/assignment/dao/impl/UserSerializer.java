package com.swe.assignment.dao.impl;


import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swe.assignment.bean.StudentBean;

public class UserSerializer implements Serializer<StudentBean[]>{
	
	public UserSerializer(){
		
	}

	@Override
	public void configure(Map configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

		@Override
	public byte[] serialize(String topic, StudentBean[] data) {
		byte[] survey = null;
		try {
			survey = new ObjectMapper().writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return survey;
	}
}