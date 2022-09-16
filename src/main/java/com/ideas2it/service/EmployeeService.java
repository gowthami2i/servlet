package com.ideas2it.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import com.ideas2it.model.Trainer;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Employee;

/**
 * <h1>EmployeeServiceImpl</h1>
 *
 * collects the returning object from EmployeeController 
 * and send to the EmployeeDeoImpl class and vise versa
 * 
 *
 * @author  Gowtham P
 * @version java 1.0
 * 
 */
public interface EmployeeService {
    
    /**
     * method is used to add Trainer 
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer Object
     * @return {@link void }
     */
    boolean addTrainer(Trainer trainer) throws Exception;

    /**
     * method is used to add Trainee 
     * @param {@link String} employeeId
     * @param {@link Trainer} trainee Object
     * @return {@link void }
     */
    boolean addTrainee(Trainee trainee)throws Exception;
   
    /**
     * method is used to get Trainer from Dao 
     * @return {@link Map<String , Trainer>} trainersFromDao object
     */
    List<Trainer> getTrainersFromDao() throws Exception;

    /**
     * method is used to get Trainee from Dao 
     * @return {@link Map<String , Trainer>} traineesFromDao object
     */
    List<Trainee> getTraineesFromDao() throws Exception;
  
    /**
     * method is used to searchTrainerDetailsById
     * @return {@link Trainer>} currentTrainer object
     */
    Trainer searchTrainerDetailsById(int EmployeeId) throws Exception;

    /**
     * method is used to searchTraineeDetailsById
     * @return {@link Trainee>} currentTrainee object
     */
    Trainee searchTraineeDetailsById(int EmployeeId) throws Exception;

    /**
     * method is used to deleteTrainerDetails
     * @param {@link String} removeEmployeeId
     * @return {@link void} 
     */
    boolean deleteTrainerDetails(int removeEmployeeId) throws Exception;
    
    /**
     * method is used to deleteTraineeDetails
     * @param {@link String} removeEmployeeId
     * @return {@link void} 
     */
    boolean deleteTraineeDetails(int removeEmployeeId) throws Exception;

    
    /**
     * method is used to updateTrainerDetails
     * @param {@link String} employeeId
     * @param {@link Trainer} searchedUpdateTrainer object
     * @return {@link void} 
     */
    boolean updatedTrainerDetails(int employeeId, Trainer searchedUpdateTrainer) throws Exception;

    /**
     * method is used to updateTraineeDetails
     * @param {@link String} employeeId
     *@param {@link Trainer} searchedUpdateTrainee object
     * @return {@link void} 
     */
    boolean updatedTraineeDetails(int employeeId, Trainee searchedUpdateTrainee) throws Exception;
    
}

