package com.ideas2it.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * <h1>Trainee </h1>
 * Trainee class is an pojo class.
 * Creating Trainee Particular data in the program
 *
 * @author  Gowtham P
 * @version java 1.0
 * 
 */
@Entity
@Table(name = "trainee")
public class Trainee extends Employee {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id",nullable = false)
    private int id;
     
    @Column(name = "skill_set")
    private String skillSet;

    @Column(name = "no_Of_Task")
    private int task;

    @ManyToMany(targetEntity = Trainer.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "trainerid_traineeid",
               joinColumns = {@JoinColumn(name = "trainee_id")})
    private List<Trainer> trainer;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setSkillSet (String skillSet){
	this.skillSet=skillSet;
    }

    public String getSkillSet () {
	return skillSet;
    }

    public void setTask(int task) {
	this.task = task;
    }

    public int getTask () {
	return task;
    }

    public void setTrainerDetails(List<Trainer> trainer) {
        this.trainer = trainer;
    }

    public List<Trainer> getTrainerDetails() {
        return trainer;
    }

}