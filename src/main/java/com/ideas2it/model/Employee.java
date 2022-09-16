package com.ideas2it.model;

import java.sql.Date;
import java.time.Period;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * <h1>Employee </h1>
 * Employee class is an pojo class.
 * Creating employee data in the program
 *
 * @author  Gowtham P
 * @version java 1.0
 * 
 */
@MappedSuperclass
public class Employee {
   
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    
    @Column(name = "mobilenumber")
    private long  mobileNumber;

    @Column(name = "date_of_joinning")
    private Date dateOfJoinning;
    
    @Column(name = "email")
    private String email;

    @Column(name = "bloodgroup")
    private String bloodGroup;

    @Column(name = "aadharnumber")
    private long aadharNumber;

    @Column(name = "pancard")
    private String panCard;

    @Column(name = "isremoved")
    @ColumnDefault("0")
    private boolean isRemoved;
    
    public void setFirstName(String firstName) {
       this.firstName = firstName;
    } 
   
    public String getFirstName() {
	return firstName;
    }
    
    public void setLastName(String lastName) {
	this.lastName = lastName;
    } 

    public String getLastName() {
	return lastName;
    }

  
    public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    }
    public Date getDateOfBirth() {
       return dateOfBirth;
    }

    public long getMobileNumber() {
	return mobileNumber;
    }
    public void setMobileNumber(long mobileNumber) {
	this.mobileNumber = mobileNumber;
    }
   
    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email=email;
    }

    public void setDateOfJoinning(Date dateOfJoinning) {
	this.dateOfJoinning = dateOfJoinning;
    }

    public Date getDateOfJoinning() {
	return dateOfJoinning;
    }

    public String getBloodGroup() {
	return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
	this.bloodGroup = bloodGroup;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {        
        this.panCard = panCard;
    }

    public long getAadharNumber() {
        return aadharNumber;
    }

    public  void setAadharNumber(long aadharNumber) {        
        this.aadharNumber = aadharNumber;
    }

    public boolean getIsRemoved() {
       return isRemoved;
    }

    public void setIsRemoved(boolean isRemoved) {
       this.isRemoved = isRemoved;
    }
   
}