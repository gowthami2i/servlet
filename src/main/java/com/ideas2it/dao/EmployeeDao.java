package com.ideas2it.dao;


import org.hibernate.criterion.Restrictions;
import org.hibernate.cfg.Configuration;
import org.hibernate.Criteria;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.sql.SQLException;

import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.dao.EmployeeDao;

/**
 * <h1>EmployeeDao</h1>
 *
 * collects the returning object from EmployeeServiceImpl and send to the Database
 *
 * @author  Gowtham P
 * @version java 1.0
 * 
 */
public interface EmployeeDao {

    /**
     * get the Trainers details in the map 
     * @return {@link  Map<String,Trainer>} trainers object
     */
    List<Trainer> getTrainerDetails() throws Exception;
   
    /**
     * get the Trainee details in the map 
     * @return {@link  Map<String,Trainee>} trainee object
     */
    List<Trainee> getTraineeDetails() throws Exception;

    /**
     * Insert Trainer in to the Database
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer Object
     * @return {@link  }
     */
     boolean insertTrainer(Trainer trainer) throws Exception;
    
    /**
     * Insert Trainee in to the Database 
     * @param {@link String} employeeId
     * @param {@link Trainee} trainee Object
     * @return {@link }
     */
    boolean insertTrainee(Trainee trainee) throws Exception;
    
    /**
     * retrieve the Trainer by EmployeeId in the map 
     * @param {@link String} employeeId
     * @return {@link Trainer} trainer Object
     */
    Trainer retrieveTrainerbyId(int trainerId);
    
    /**
     * retrieve the Trainee by EmployeeId in the map 
     * @param {@link String} employeeId
     * @return {@link Trainee} trainee Object
     */
    Trainee retrieveTraineebyId(int traineeId) throws Exception;

    /**
     * Update the Trainer by using EmployeeId and user update object in the  
     * @param {@link String} employeeId
     * @param {@link Trainer} searchedUpdateTrainer Object
     */    
    boolean modifyTrainerDetailsById( Trainer trainer);

    /**
     * Update the Trainee by  using EmployeeId and user updateobject  
     * @param {@link String} employeeId
     * @param {@link Trainee} searchedUpdateTrainee Object
     */
    boolean modifyTraineeDetailsById(int traineeId, Trainee trainee) throws Exception;
    
    /**
     * Delete the Trainer by  using EmployeeId  
     * @param {@link String} employeeId
     */       
    boolean deleteTrainerById(int removeEmployeeId) throws Exception;

    /**
     * Delete the Trainee by using EmployeeId  
     * @param {@link String} employeeId
     * @return{@void}
     */
    boolean deleteTraineeById(int removeEmployeeId) throws Exception;

}