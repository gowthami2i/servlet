package com.ideas2it.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
import javax.validation.constraints.NotEmpty;

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
@Table(name ="trainers")
public class Trainer extends Employee  {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id",nullable = false)
    private int id; 
 
    @Column(name = "no_of_project")
    private int project;

    @Column(name = "experience")
    private int experience;

    //@ManyToMany(targetEntity = Trainee.class,cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @ManyToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinTable(name = "trainerid_traineeid",
               joinColumns = {@JoinColumn(name= "trainer_id")},
            inverseJoinColumns = { @JoinColumn(name = "trainee_id") })
    //@Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
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

   /* @Override
    public String toString() {
        boolean traineeDetails;
       /* return "{\" id\":\"" +getId() +

                "\",\" firstName\":\"" + getFirstName() +
                "\",\" lastName\":\"" + getLastName() +
                "\",\" DateOfBirth\":\"" + getDateOfBirth() +
                "\",\" email\":\"" + getEmail() +
                "\",\" mobileNumber\":\"" + getMobileNumber() +
                "\",\" aadharCardNumber\":\"" + getAadharNumber() +
                "\",\" panCard\":\"" + getPanCard() +
                "\",\" project\":\"" + getProject() +

                /*"\",\" Trainees\":\"" +getTraineeDetails().stream().flatMap(trainer ->
                Stream.of("\"" + trainer.getId() + "\":\"" +
                        trainer.getFirstName() + "\"")).collect(Collectors.toSet())+
                "\",\"}";
    }*/
}

