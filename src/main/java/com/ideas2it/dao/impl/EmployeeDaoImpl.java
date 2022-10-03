package com.ideas2it.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.HibernateException; 
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.factory.EmployeeFactory;
import org.springframework.stereotype.Repository;

/**
 * <h1>EmployeeDaoImpl</h1>
 *
 * The EmployeeDaoImpl class is used to collect the returning object from EmployeeServiceImpl
 * and send to the Database 
 *
 * @author  Gowtham P
 * @version java 1.0
 * 
 */
@Repository
public class EmployeeDaoImpl {
    /**
     * method is used to Insert Trainer into Database
     * @param {@link Trainer}trainer object
     * @return {@link }
     */
    public boolean insertTrainer(Trainer trainer) throws Exception {

        boolean isInsertTrainer = false;       
        Session session = EmployeeFactory.getEmployeeFactory().openSession();   
        Transaction transaction = null; 
        try {
            transaction = session.beginTransaction();
            System.out.println(trainer.getFirstName());
            session.save(trainer);
            isInsertTrainer = true;
            transaction.commit();   
        } catch (HibernateException e) {

            transaction.rollback();
            throw e;
 
        } finally {
            session.close(); 
        }
        return isInsertTrainer;    
   }

    /**
     * method is used to Insert Trainee into Database
     * @param {@link Trainee}trainee object
     * @return {@link }
     */
    public boolean insertTrainee(Trainee trainee)  throws Exception{ 
    
       boolean isInsertTrainee = false;
       Session session = EmployeeFactory.getEmployeeFactory().openSession();
       Transaction transaction = null;
                   
       try {
           transaction = session.beginTransaction();
           session.save(trainee); 
           isInsertTrainee = true;
           transaction.commit();   
       } catch (HibernateException e) {

            transaction.rollback();
            throw e;
 
       } finally {
           session.close(); 
       }  
       return isInsertTrainee;    
    }

    /**
     * method is used to getTrainerDetails into Database
     * @return {@link List<Trainer>}trainer object 
     */ 
    public List<Trainer> getTrainerDetails()  throws Exception {
        
        List<Trainer> trainers = new ArrayList<>();

        Session session = EmployeeFactory.getEmployeeFactory().openSession();
        //Transaction transaction = null;
      
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Trainer.class);
            criteria.add(Restrictions.eq("isRemoved", false));
            trainers = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();   
        } catch (HibernateException e) {

           // transaction.rollback();
            throw e;
 
        } finally {
            session.close(); 
        }
       return trainers;
    } 

    /**
     * method is used to getTraineeDetails into Database
     * @return {@link List<Trainee>}trainee object 
     */ 
    public List<Trainee> getTraineeDetails()  throws Exception {
    
        List<Trainee> trainees = new ArrayList<>();
        Session session = EmployeeFactory.getEmployeeFactory().openSession();
        Transaction transaction = null;
                                    
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Trainee.class);
            criteria.add(Restrictions.eq("isRemoved", false));
            trainees = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();  
        } catch (HibernateException e) {

            transaction.rollback();
            throw e;

        } finally {
            session.close(); 
        }
       return trainees;
    } 

    /**
     * method is used to retrieve the Trainer by EmployeeId in the map 
     * @param {@link String} employeeId
     * @return {@link Trainer} trainer Object
     */          
    public Trainer retrieveTrainerbyId(int trainerId)  throws Exception {
      
        Transaction transaction = null;
        Trainer trainer = null;
        Session session = null;
        try {
            session = EmployeeFactory.getEmployeeFactory().openSession();
            transaction = session.beginTransaction();
            trainer = (Trainer) session.get(Trainer.class, trainerId);
            Criteria criteria = session.createCriteria(Trainer.class);
            criteria.add(Restrictions.eq("isRemoved", false)).list();
            return trainer;
             
        } catch (HibernateException e) {

            transaction.rollback();
            throw e;

        } finally {
            session.close();
        }       
       
    }

    /**
     * method is used to retrieve the Trainee by EmployeeId in the map 
     * @param {@link String} employeeId
     * @return {@link Trainee} trainee Object
     */
    public Trainee retrieveTraineebyId(int traineeId) throws Exception {
   
        Transaction transaction = null;
        Trainee trainee = null;      
        try(Session session = EmployeeFactory.getEmployeeFactory().openSession()) {
             transaction = session.beginTransaction();   
             trainee = (Trainee)session.get(Trainee.class,traineeId);
             Criteria criteria = session.createCriteria(Trainee.class);
             criteria.add(Restrictions.eq("isRemoved", false));       
             return trainee;
        } catch (HibernateException e) {

            transaction.rollback();
            throw e;
        }
          
    }

    /**
     * method is used to delete the Trainer by  using EmployeeId  
     * @param {@link String} employeeId
     */
    public boolean deleteTrainerById(int removeEmployeeId) throws Exception {
        Transaction transaction = null;
        System.out.println(removeEmployeeId);
        boolean isDeleteTrainer = false;        
        try (Session session = EmployeeFactory.getEmployeeFactory().openSession()) {

            transaction = session.beginTransaction();
            Trainer trainer = (Trainer) session.get(Trainer.class, removeEmployeeId); 
            Criteria criteria = session.createCriteria(Trainer.class);
            criteria.add(Restrictions.eq("isRemoved", false));
            trainer.setIsRemoved(true);
            session.update(trainer);
            isDeleteTrainer = true;
            transaction.commit();
        } catch (HibernateException e) {

            transaction.rollback();
            throw e;
        } 
        return isDeleteTrainer;
    }

    /**
     * method is used to delete the Trainee by using EmployeeId  
     * @param {@link String} employeeId
     * @return{@void}
     */
    public boolean deleteTraineeById(int removeEmployeeId) throws Exception {
       Transaction transaction = null;
       boolean isDeleteTrainee = false;
       try (Session session = EmployeeFactory.getEmployeeFactory().openSession();) {
                                
           transaction = session.beginTransaction();
           Trainee trainee = (Trainee) session.get(Trainee.class, removeEmployeeId); 
           Criteria criteria = session.createCriteria(Trainee.class);
           criteria.add(Restrictions.eq("isRemoved", false));
           trainee.setIsRemoved(true);
           session.update(trainee);
           isDeleteTrainee = true;
           transaction.commit();
       } catch (HibernateException e) {

            transaction.rollback();
            throw e;
       }  
       return isDeleteTrainee;     
    }

    /**
     * method is used to Update the Trainer by  using EmployeeId and user update object in the  
     * @param {@link String} employeeId
     * @param {@link Trainer} searchedUpdateTrainer Object
     */
    public <session> boolean modifyTrainerDetailsById(int trainerId, Trainer trainer)  throws Exception {

        Transaction transaction = null;
        Session session = null;
        boolean isUpdateTrainer = false;

        try {
            session = EmployeeFactory.getEmployeeFactory().openSession();
           Trainer trainers = (Trainer) session.get(Trainer.class, trainerId);
            transaction = session.beginTransaction();        
           trainers.setFirstName(trainer.getFirstName());
            trainers.setLastName(trainer.getLastName());
            trainers.setEmail(trainer.getEmail());
            trainers.setDateOfBirth(trainer.getDateOfBirth());
            trainers.setMobileNumber(trainer.getMobileNumber());
            trainers.setBloodGroup(trainer.getBloodGroup());
            trainers.setPanCard(trainer.getPanCard());
            trainers.setTraineeDetails(trainer.getTraineeDetails());
            session.update(trainers);
            isUpdateTrainer = true;
            transaction.commit();
        } catch (HibernateException e) {

            transaction.rollback();
            throw e;
        } finally { 
            session.close();
        }        
        return isUpdateTrainer;         
    }

    /**
     * method is used to Update the Trainee by  using EmployeeId and user updateobject  
     * @param {@link String} employeeId
     * @param {@link Trainee} searchedUpdateTrainee Object
     */
    public boolean modifyTraineeDetailsById(int traineeId, Trainee trainee)  throws Exception {

        Transaction transaction = null;
        boolean isUpdateTrainee = false;
        try(Session session = EmployeeFactory.getEmployeeFactory().openSession();) {
       
            Trainee trainees = (Trainee) session.get(Trainee.class, traineeId);
            transaction = session.beginTransaction();
            trainees.setFirstName(trainee.getFirstName());
            trainees.setLastName(trainee.getLastName());
            trainees.setEmail(trainee.getEmail());
            trainees.setDateOfBirth(trainee.getDateOfBirth());
            trainees.setMobileNumber(trainee.getMobileNumber());
            trainees.setBloodGroup(trainee.getBloodGroup());
            trainees.setPanCard(trainee.getPanCard());
            trainees.setTrainerDetails(trainee.getTrainerDetails());
            session.update(trainees);
            isUpdateTrainee = true;
            transaction.commit();
        } catch (HibernateException e) {

            transaction.rollback();
            throw e;
        }        
        return isUpdateTrainee;              
    }
 
    
}



