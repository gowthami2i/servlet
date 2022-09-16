package com.ideas2it.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;

/**
 * <h1>Trainer </h1>
 * trainer class is an pojo class.
 * Creating Trainer data in the program
 *
 * @author  Gowtham P
 * @version java 1.0
 * 
 */
@Entity
@Table(name ="trainer")
public class Trainer extends Employee  {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id",nullable = false)
    private int id; 
 
    @Column(name = "no_of_project")
    private int project;

    @Column(name = "experience")
    private int experience;
      
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "trainerid_traineeid",
               joinColumns = {@JoinColumn(name= "trainer_id")})
    private List<Trainee> trainee;
    
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setProject(int project) {
	this.project = project;    
    }

    public int getProject () {
	return project;	
    }

    public void setExperience (int experience) {
	this.experience = experience;
    }
 
    public int getExperience () {
	return experience;
    }

    public void setTraineeDetails(List<Trainee> trainee) {
        this.trainee = trainee;
    }

    public List<Trainee> getTraineeDetails() {
        return trainee;
    }
  
}

