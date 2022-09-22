package com.ideas2it.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ideas2it.model.Trainer;
import com.ideas2it.model.Trainee;
import com.ideas2it.dao.impl.EmployeeDaoImpl;
import com.ideas2it.service.EmployeeService;

/**
 * <h1>EmployeeServiceImpl</h1>
 *
 * The EmployeeServiceImpl class is used to collect the returning object from EmployeeController
 * and send to the EmployeeDeoImpl class and vise versa
 *
 * @author  Gowtham P
 * @version java 1.0
 * 
 */
public class EmployeeServiceImpl implements EmployeeService {

    private static EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();
    
    /**
     * method is used to add Trainer 
     * @param {@link String} employeeId
     * @param {@link Trainer} trainer Object
     * @return {@link }
     */ 
    @Override
    public  boolean addTrainer( Trainer trainer) throws Exception {

        boolean isAddTrainer = employeeDaoImpl.insertTrainer(trainer);	
        return 	isAddTrainer;
    }

    /**
     * method is used to add Trainee 
     * @param {@link String} employeeId
     * @param {@link Trainer} trainee Object
     * @return {@link  }
     */
    @Override
    public boolean addTrainee(Trainee trainee) throws Exception{

        boolean isAddTrainee = employeeDaoImpl.insertTrainee( trainee);
        return 	isAddTrainee;
    }

    /**
     * method is used to get Trainer from Dao
     * @return {@link Map<String , Trainer>} trainers object
     */

    @Override 
    public List<Trainer> getTrainersFromDao() throws Exception {

        List<Trainer> trainers = employeeDaoImpl.getTrainerDetails();
        return trainers;
    }

    /**
     * method is used to get Trainee from Dao 
     * @return {@link Map<String , Trainer>} trainees
     */
    @Override
    public List<Trainee> getTraineesFromDao() throws Exception{

       List<Trainee> trainees = employeeDaoImpl.getTraineeDetails();
        return trainees;
    } 

    /**
     * method is used to searchTrainerDetailsById
     * @return {@link Trainer>} currentTrainer object
     */
    @Override
    public Trainer searchTrainerDetailsById(int EmployeeId) throws Exception{
        Trainer currentTrainer = null;
        if (null != employeeDaoImpl.retrieveTrainerbyId(EmployeeId)) {
            currentTrainer = employeeDaoImpl.retrieveTrainerbyId(EmployeeId);
        }  
        return currentTrainer;
    }

    /**
     * method is used to searchTraineeDetailsById
     * @return {@link Trainee>} currentTrainee object
     */
    @Override  
    public Trainee searchTraineeDetailsById(int EmployeeId) throws Exception {
        Trainee currentTrainee = null;
        if (null != employeeDaoImpl.retrieveTraineebyId(EmployeeId)) {
            currentTrainee = employeeDaoImpl.retrieveTraineebyId(EmployeeId);
            return currentTrainee;
        }  
        return currentTrainee;
    }

    /**
     * method is used to deleteTrainerDetails
     * @param {@link String} removeEmployeeId
     * @return {@link }
     */
    @Override
    public boolean deleteTrainerDetails(int employeeId) throws Exception {
         
        boolean isDeleted = employeeDaoImpl.deleteTrainerById(employeeId);  
        return isDeleted;
    }

    /**
     * method is used to deleteTraineeDetails
     * @param {@link String} removeEmployeeId
     * @return {@link }
     */
    @Override
    public boolean deleteTraineeDetails(int removeEmployeeId) throws Exception {

       boolean isDeleted = employeeDaoImpl.deleteTraineeById(removeEmployeeId);
       return isDeleted;
    }
 
    /**
     * method is used to updateyTrainerDetails
     * @param {@link String} employeeId
     * @param {@link Trainer} searchedUpdateTrainer object
     * @return {@link }
     */
    @Override
    public boolean updatedTrainerDetails(int employeeId, Trainer searchedUpdateTrainer) throws Exception {
         
       boolean isUpdateTrainer = employeeDaoImpl.modifyTrainerDetailsById(employeeId, searchedUpdateTrainer);  
       return isUpdateTrainer;     
    } 

    /**
     * method is used to updateTraineeDetails
     * @param {@link String} employeeId
     * @param {@link Trainer} searchedUpdateTrainee object
     * @return {@link }
     */                               
    @Override
    public boolean updatedTraineeDetails(int employeeId, Trainee searchedUpdateTrainee) throws Exception{
         
        boolean isUpdateTrainee = employeeDaoImpl.modifyTraineeDetailsById(employeeId, searchedUpdateTrainee);
        return isUpdateTrainee;
    }
    public Map<String, Object> getTrainerObject(Trainer trainer) {
        List<Map<String, Object>> trainee = new ArrayList<>();
        List<Trainee> list = trainer.getTraineeDetails();

        for(Trainee traineeList : list){
            Map<String,Object> listTrainee = new HashMap<>();
            listTrainee.put("traineeId",traineeList.getId());
            listTrainee.put("Trainee Name",traineeList.getFirstName());
            trainee.add(listTrainee);

        }
        Map<String,Object> map = new HashMap<>();
        map.put("trainerId",trainer.getId());
        map.put("TrainerName",trainer.getFirstName());
        map.put("Trainer email",trainer.getEmail());
        map.put("Trainer Blood Group" ,trainer.getBloodGroup());
        map.put("Trainer Pancard",trainer.getPanCard() );
        map.put("Trainer Mobile Number", trainer.getMobileNumber());

        map.put("trainees", trainee);
        return map;
    }
    public Map<String, Object> getTraineeObject(Trainee trainee) {
        List<Map<String, Object>> trainer = new ArrayList<>();
        List<Trainer> list = trainee.getTrainerDetails();

        for(Trainer trainerList : list){
            Map<String,Object> listTrainer = new HashMap<>();
            listTrainer.put("trainerId",trainerList.getId());
            listTrainer.put("Trainer Name",trainerList.getFirstName());
            trainer.add(listTrainer);

        }
        Map<String,Object> map = new HashMap<>();
        map.put("traineeId",trainee.getId());
        map.put("TraineeName",trainee.getFirstName());
        map.put("Trainee email",trainee.getEmail());
        map.put("Trainee Blood Group" ,trainee.getBloodGroup());
        map.put("Trainee Pancard",trainee.getPanCard() );
        map.put("Trainee Mobile Number", trainee.getMobileNumber());

        map.put("trainer", trainer);
        return map;
    }


}