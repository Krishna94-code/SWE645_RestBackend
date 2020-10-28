package com.swe.assignment.dao;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.swe.assignment.bean.StudentBean;

/**
 * Class to store student form data into the database equivalent object
 * 
 * @author ambily
 *
 */

@Entity
@Table(name = "student_data")
public class StudentDAO {
	@Id
	int id;
	@Column(name = "name")
	String userName;
	@Column(name = "address")
	String streetAddress;
	String city;
	String state;
	int zip;
	String telephone;
	String email;
	String url;
	Date dateOfSurvey;
	@Column(name = "likes")
	String likedMost;
	@Column(name = "interests")
	String getInterested;
	@Column(name = "gradmonth")
	String gradMonth;
	@Column(name = "gradyear")
	String gradYear;
	String comments;
	String recommendation;
	/**
	 * getters and setters
	 *
	 */
	public String getLikedMost() {
		return likedMost;
	}

	public void setLikedMost(String likedMost) {
		this.likedMost = likedMost;
	}

	public String getGetInterested() {
		return getInterested;
	}

	public void setGetInterested(String getInterested) {
		this.getInterested = getInterested;
	}

	public String getGradMonth() {
		return gradMonth;
	}

	public void setGradMonth(String gradMonth) {
		this.gradMonth = gradMonth;
	}

	public String getGradYear() {
		return gradYear;
	}

	public void setGradYear(String gradYear) {
		this.gradYear = gradYear;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDateOfSurvey() {
		return dateOfSurvey;
	}

	public void setDateOfSurvey(Date dateOfSurvey) {
		this.dateOfSurvey = dateOfSurvey;
	}

	public StudentDAO() {

	}

	/**
	 * Constructor to create StudentDAO object based on a studentBean passed to it
	 * 
	 * @param studentBean
	 */
	public StudentDAO(StudentBean studentBean) throws Exception {
		this.userName = studentBean.getUserName();
		this.streetAddress = studentBean.getStreetAddress();
		this.city = studentBean.getCity();
		try {
			java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(studentBean.getDateOfSurvey());
			this.dateOfSurvey = new Date(date.getTime());
		} catch (ParseException e) {
			// if any data parsing error log and re throw the exception
			System.out.println(e.getMessage());
			throw e;
		}
		this.email = studentBean.getEmail();
		this.state = studentBean.getState();
		this.id = Integer.parseInt(studentBean.getId());
		this.url = studentBean.getUrl();
		this.zip = studentBean.getZip();
		this.telephone = studentBean.getTelephone();
		List<String> stringList = Arrays.asList(studentBean.getLikedMost());
		this.likedMost = String.join(",", stringList);
		this.getInterested = studentBean.getGetInterested();
		this.gradMonth = studentBean.getGradMonth();
		this.gradYear = studentBean.getGradYear();
		this.recommendation = studentBean.getRecommendation();
		this.comments = studentBean.getComments();
	}

	/**
	 * Method to convert the StudentDatabase object back to student bean object
	 * 
	 * @return StudentBean object
	 */
	public StudentBean convert() {
		StudentBean bean = new StudentBean();
		bean.setCity(this.getCity());
		bean.setUserName(this.getUserName());
		bean.setStreetAddress(this.getStreetAddress());
		bean.setDateOfSurvey(this.getDateOfSurvey().toString());
		bean.setEmail(this.getEmail());
		bean.setState(this.getState());
		bean.setId(this.getId() + "");
		bean.setUrl(this.getUrl());
		bean.setZip(this.getZip());
		bean.setTelephone(this.getTelephone());
		String[] likes = (this.likedMost != null) ? this.likedMost.split(",") : null;
		bean.setLikedMost(likes);
		bean.setGetInterested(this.getInterested);
		bean.setGradMonth(this.gradMonth);
		bean.setGradYear(this.gradYear);
		bean.setRecommendation(this.recommendation);
		bean.setComments(this.comments);
		return bean;
	}

}
