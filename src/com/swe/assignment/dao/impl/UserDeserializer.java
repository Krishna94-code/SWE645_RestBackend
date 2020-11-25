package com.swe.assignment.dao.impl;

import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swe.assignment.bean.StudentBean;

public class UserDeserializer implements Deserializer<StudentBean[]> {

	public UserDeserializer() {

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
	public StudentBean[] deserialize(String topic, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
		StudentBean[] surveys = null;
		try{
			surveys = mapper.readValue(data, StudentBean[].class);
		}catch (Exception e){
			e.printStackTrace();
		}		
		
		
		return surveys;
	}
}
