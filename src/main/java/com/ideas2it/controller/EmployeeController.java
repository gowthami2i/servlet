package com.ideas2it.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeParseException;
import java.sql.Date;

import javafx.css.Styleable;
import org.hibernate.HibernateException;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainer;
import com.ideas2it.model.Trainee;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.util.EmployeeUtil;
import com.ideas2it.exception.EmailMismatchException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h1>Employee Controller</h1>
 * The Employee Controller class is used to get infromation from the user  and
 * display the output on the screen
 *
 * @author  Gowtham P
 * @version java 1.0
 * 
 */
public class EmployeeController extends HttpServlet {

    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    static final EmployeeService employeeServiceImpl = new EmployeeServiceImpl();

    /**
     * method is used to Show the Menu details
     * 
     * @return {@link }
     */
    public void viewMenu() {
       
        Scanner scanner = new Scanner(System.in);       
        try {

            boolean employeeData = true;
            while (employeeData) {
                logger.info("welcome  to IDEAS2IT Employee Details");
	            logger.info("\n");
	            logger.info("Enter 1 to Add Employee as a Trainer ");
	            logger.info("Enter 2 to Add Employee Trainee ");
                logger.info("Enter 3 to Search Employee ");
	            logger.info("Enter 4 to update details");
                logger.info("Enter 5 to Remove Employee");
                logger.info("Enter 6 Display Details");
                logger.info("Enter 7 to Assign Employee");	
                logger.info("Enter 8 to Unassighn Employee");      	
	            logger.info("please Enter your Selection");
	        int menuOption=scanner.nextInt();
                
                switch (menuOption) {

                    case 1 :

                        logger.info("Enter how many Trainers to be added");                             			
	                int noOfTrainer = scanner.nextInt();
                        for (int i = 1; i <= noOfTrainer; i++) {
 
       		            Trainer trainers = getTrainersDetailsFromUser(scanner);                      
                            boolean isAddTrainer = employeeServiceImpl.addTrainer(trainers) ;                
                            if (isAddTrainer) {  
                                    logger.info("Inserted succesfully");
                            } else {
                                    logger.error("not Inserted");
                            } 
	                }		    
 	                break;
                     
		    case 2 :

                        logger.info("Enter how many Trainees to be added");                             			
	                int noOfTrainee = scanner.nextInt();
		        for (int i=1; i <= noOfTrainee; i++) {

		            Trainee trainees = getTraineesDetailsFromUser(scanner);
                            boolean isAddTrainee = employeeServiceImpl.addTrainee(trainees);
                            if (isAddTrainee) {  
                                    logger.info("Inserted succesfully");
                            } else {
                                    logger.error("not Inserted");
                            }           
 		        }	                                                                  		    
	                break; 
                             
                   /* case 3 :
 
                        logger.info("Which details you want to display ? ");
                        logger.info("1.Trainer");
                        logger.info("2.Trainee");
		        int displayEmployee = scanner.nextInt();
                        Trainer trainer = null;
                        Trainee trainee = null;
                  
                        if (displayEmployee== 1) {
                                         
                            logger.info("Enter the Employee Id to search Trainer");
                            int employeeIdTrainer = scanner.nextInt();
                            trainer = employeeServiceImpl.searchTrainerDetailsById(employeeIdTrainer);
                                                       
                            if (trainer != null) {
                                getTrainerDetailsById( trainer);
                                /*StringBuilder stringBuilder = new StringBuilder();
                                logger.info("{}", stringBuilder.append("Trainer First Name   :"+trainer.getFirstName()+"\n")
                                                               .append("Trainer Email        :"+ trainer.getEmail()+"\n") 
                                                               .append("Trainer Mobile Number:"+ trainer.getMobileNumber()+"\n")
                                                               .append("Trainer Blood Group  :"+ trainer.getBloodGroup()+"\n\n"));
                                                                       
                                for(int i = 0; i<trainer.getTraineeDetails().size();i++) {
                                    
                                    logger.info("{}",(String.format("%d ."+"Trainee Name :"+trainer.getTraineeDetails().get(i).getFirstName()+"\n"
                                                      +"Trainee Id :" +trainer.getTraineeDetails().get(i).getId()+"\n", i+1)));
                                }
                            } else {
                                                           
                                logger.error("Invalid Employee ID");
                            }
                        } else if (displayEmployee== 2) {
                                                            
                            logger.info("Enter the Employee Id to search Trainee");
                            int employeeIdTrainee = scanner.nextInt();
                            trainee = employeeServiceImpl.searchTraineeDetailsById(employeeIdTrainee);
                                                                          
                         if (trainee != null) {
                                                                             
                             StringBuilder stringBuilder = new StringBuilder();
                             logger.info("{}", stringBuilder.append("Trainee First Name   :"+trainee.getFirstName()+"\n")
                                                            .append("Trainee Email        :"+ trainee.getEmail()+"\n") 
                                                            .append("Trainee Mobile Number:"+ trainee.getMobileNumber()+"\n")
                                                            .append("Trainee Blood Group  :"+ trainee.getBloodGroup()));
                                                                                                                               
                             for (int i = 0; i<trainee.getTrainerDetails().size();i++) {
                                                                                                                                                                                                                                                                        
                                logger.info("{}","\n"+"Trainer Name : " +trainee.getTrainerDetails().get(i).getFirstName()+"\n"
                                                     +"Trainer Id : "+ trainee.getTrainerDetails().get(i).getId()+"\n\n");
                             }
                                                                  
                         } else {
                            logger.error("Invalid Employee ID");
                         }
                    }
                        break;*/

                    case 4 :

                        logger.info("Which details you want to update ? ");
                        logger.info("1.Trainer");
                        logger.info("2.Trainee");
		        int selectEmployeeType = scanner.nextInt();
                        Trainer updateTrainer = null;
                        Trainee updateTrainee = null;
                        logger.info("Enter the Employee Id to search Trainer");
                        int employeeId = scanner.nextInt();

                        if(selectEmployeeType == 1) {     
                   
                            updateTrainer = employeeServiceImpl.searchTrainerDetailsById(employeeId);
                        } else if (selectEmployeeType == 2) {
                            updateTrainee = employeeServiceImpl.searchTraineeDetailsById(employeeId);
                        } else {
                                System.out.println("Enter the valid data");
                        }

                        if (updateTrainer != null || updateTrainee != null) {

                            if (selectEmployeeType == 1) {

                                Trainer  updatedTrainerDetails = updateTrainerDetails(scanner , updateTrainer); 
                                boolean isUpdateTrainer = employeeServiceImpl.updatedTrainerDetails(employeeId , updatedTrainerDetails);
                                if (isUpdateTrainer) {  
                                    logger.info("updated succesfully");
                                } else {
                                    logger.error("not updated enter the valid trainer Id");
                                }
                            } else if (selectEmployeeType == 2) {

                                Trainee updatedTraineeDetails= updateTraineeDetails(scanner , updateTrainee) ; 
                                boolean isUpdateTrainee = employeeServiceImpl.updatedTraineeDetails(employeeId , updatedTraineeDetails); 
                                if (isUpdateTrainee) {  
                                    logger.info("updated succesfully");
                                } else {
                                    logger.error("not updated enter the valid trainer Id");
                                }
                            } 
                        }
                       break; 
 
                   case 5 :
                   
                       logger.info("Which details you want to Remove ? ");
                       logger.info("1.Trainer");
                       logger.info("2.Trainee");
		       int employee = scanner.nextInt();
                   
                       if (employee == 1) {

                           logger.info("Enter the Employee Id to remove Trainer");
                           int removeEmployeeId = scanner.nextInt(); 
                           boolean isDeleted = employeeServiceImpl.deleteTrainerDetails(removeEmployeeId);
                           if (isDeleted) {
                               logger.info("Suceesfully removed ");
                           } else {
                               logger.error("not removed enter valid Trainer Id");
                           } 
                           
                       } else if (employee == 2) {
                        
                           logger.info("Enter the Employee Id to remove Trainee");
                           int removeEmployeeId = scanner.nextInt();
                           boolean isDeleted = employeeServiceImpl.deleteTraineeDetails(removeEmployeeId);
                            
                           if (isDeleted) {
                               logger.info("Suceesfully removed ");
                           } else {
                               logger.error("not removed enter valid Trainee Id");
                           }      
                       }
                       break;

                   case 6 :
                                  
                       logger.info("Which details you want to display ? ");
                       logger.info("1.Trainer");
                       logger.info("2.Trainee");
		       int display = scanner.nextInt();
                                                            
		       if (display == 1) {



                           logger.info("----------------------------");
                       } else if (display == 2) {
                                                                                 
 	                   displayTraineeDetails();
                           logger.info("----------------------------");
	               }
                       break;

                   case 7 :

                       boolean isCheckValue = false;
                       while (!isCheckValue) {
                                          
                           try {
                          
                               System.out.println("Enter 1 to assign Trainer to Trainee");
                               System.out.println("Enter 2 to assign Trainee to Trainer");
                               int assignUserInput = scanner.nextInt();
                                                             
                               if (assignUserInput == 1) { 
                                   assignTraineesToTrainer(scanner);
                               } else if(assignUserInput == 2){
                                   assignTrainersToTrainee(scanner);
                               } else {
                               logger.info("Enter the valid input");
                               }
                               isCheckValue = true;
                           } catch (NumberFormatException e) {
                                                             
                               System.out.println(e);  
                           }        
	               }
                       break; 
 
                   case 8 :

                       boolean isCheckUnAssign = false;
                       while (!isCheckUnAssign) {

                           try {

                               System.out.println("Enter 1 to unassign Trainer to Trainee");
                               System.out.println("Enter 2 to unassign Trainee to Trainer");
                               int unAssignUserInput = scanner.nextInt();
                                                             
                               if (unAssignUserInput == 1) {
                                   unAssignTrainerToTrainee(scanner);
                               } else if(unAssignUserInput == 2){
                                   unAssignTraineeToTrainer(scanner);
                               } else {
                                   logger.error("Enter the valid input");
                                   isCheckValue = true;
                               }


                           } catch (NumberFormatException e) {
                               System.out.println(e);    
                           }
                       }
                       break;         
	           default:

	               logger.error("invalid data");


                }
	    
                logger.info("do you want to continue ? yes/No");
	        String menuValue = scanner.next();
                if (menuValue.equalsIgnoreCase("yes")) {
                    employeeData = true;
                } else  {
                    employeeData =false;                 
	        }  
            } 
        } catch (HibernateException exception) {
	    
            System.out.println(exception);
            viewMenu();
        } catch (NullPointerException exception) {

            logger.error("{}","invalid input "+exception);
            viewMenu();
        } catch (InputMismatchException exception) {

            logger.error("{}","invalid input "+exception);
            viewMenu();
        } catch (Exception exception) {

            logger.error("{}","invalid input "+exception  );
            viewMenu();
        }

    } 
    /**
     * method is used to get Employee Common information by the user
     * @param {@link Scanner}scanner object 
     * @param {@link EmployeeService} employeeServiceImpl Object
     * @return {@link Trainer} trainer object
     */
    public Employee getEmployeeDetailsFromUser (Scanner scanner) {
  
        Employee employee = new Employee();
        try {
            logger.info("Enter the FirstName");
            String firstName  = getName(scanner);          
            employee.setFirstName(firstName);                                   
            logger.info("Enter the LastName");
            String lastName = getName(scanner);
            employee.setLastName(lastName);                                                                                                    
            logger.info("Enter the Date of Birth in the format(yyyy-mm-dd)");
            Date dateOfBirth = Date.valueOf(getDate(scanner));
            employee.setDateOfBirth(dateOfBirth);                                                                                   
            long mobileNumber = getMobileNumber(scanner);
            employee.setMobileNumber(mobileNumber);                                                                       
            String emailId = getEmail(scanner);
            employee.setEmail(emailId);                                                                                                             
            long validatedAadharNumber = Long.parseLong(getAadharNumber(scanner));
            employee.setAadharNumber(validatedAadharNumber);                                              
            String panCard = getPanCard(scanner); 
            employee.setPanCard(panCard);              
            logger.info("Enter the Date of Joinning in the format(yyyy-mm-dd)");
            Date dateOfJoinning = Date.valueOf(getDate(scanner));
            employee.setDateOfJoinning(dateOfBirth);                                                                                                                             
            String bloodGroup = getBloodGroup(scanner);
            employee.setBloodGroup(bloodGroup);               	                
        } catch (Exception e) {
            System.out.println(e);
        }
        return employee;
    }
   
    /**
     * method is used to get Trainer information by the user
     * @param {@link Scanner}scanner object 
     * @param {@link EmployeeService} employeeServiceImpl Object
     * @return {@link Trainer} trainer object
     */
    public Trainer getTrainersDetailsFromUser(Scanner scanner) throws Exception {
        Trainer trainers = new Trainer();
        try {
                                                                     
            Employee employee = getEmployeeDetailsFromUser (scanner);                                                                                                   
            trainers.setFirstName(employee.getFirstName());
            trainers.setLastName(employee.getLastName());
            trainers.setDateOfBirth(employee.getDateOfBirth());
            trainers.setMobileNumber(employee.getMobileNumber());
            trainers.setEmail(employee.getEmail());
            trainers.setAadharNumber(employee.getAadharNumber());
            trainers.setPanCard(employee.getPanCard());
            trainers.setDateOfJoinning(employee.getDateOfJoinning());
            trainers.setBloodGroup(employee.getBloodGroup());                                                            
            logger.info("Enter your Experience");
            scanner.nextLine();
                                                         
	    while (!scanner.hasNext("[0-9]{0,2}")) {
                logger.info("invalid Experience Enter the valid Experience ");
                scanner.nextLine();
            }
	    int experience = scanner.nextInt();
	    trainers.setProject(experience);                                            
 	    logger.info("Enter your no of Project"  );
            scanner.nextLine();

	    while (!scanner.hasNext("[0-9]{0,2}")) {
                logger.info("Enter the valid Project");
                scanner.nextLine();
            }
	    int project = scanner.nextInt();
	    trainers.setProject(project);
	    logger.info("-----------------------------------");          
        } catch (Exception e) {
            throw  e;
        }
        return trainers;
    }   

       
    /**
     * method is used to get Trainee  information by the user
     * @param {@link Scanner}scanner object
     * @param {@link EmployeeService} employeeServiceImpl Object
     * @return {@link Trainee} trainee object
     */
    public Trainee getTraineesDetailsFromUser(Scanner scanner) throws Exception {         
        Trainee trainees = new Trainee();
        try {
     
            Employee employee = getEmployeeDetailsFromUser (scanner);                                                                                                     
            trainees.setFirstName(employee.getFirstName());
            trainees.setLastName(employee.getLastName());
            trainees.setDateOfBirth(employee.getDateOfBirth());
            trainees.setMobileNumber(employee.getMobileNumber());
            trainees.setEmail(employee.getEmail());
            trainees.setAadharNumber(employee.getAadharNumber());
            trainees.setPanCard(employee.getPanCard());
            trainees.setDateOfJoinning(employee.getDateOfJoinning());
            trainees.setBloodGroup(employee.getBloodGroup());

	    logger.info("Enter your Skillset");
            scanner.nextLine();
	    while (!scanner.hasNext("[a-zA-Z]{0,15}")) {
                logger.error("invalid  Enter the valid Skillset ");
                scanner.nextLine();
            }
	    String skillSet = scanner.next();
	    trainees.setSkillSet(skillSet);	                
                                                            
	    logger.info("Enter your Number of Trainning Task");
            scanner.nextLine();
	    while (!scanner.hasNext("[0-9]{0,2}")) {
                logger.error("invalid Experience Enter the valid Trainning Task ");
                scanner.nextLine();
            }
	    int task = scanner.nextInt();
	    trainees.setTask(task);
                                                      
        } catch (Exception e) {
            throw e;
        }        	
	logger.info("-----------------------------------");
	logger.info("\n");
        return trainees;      
       	
    }

    /**
     * method is used to display the Trainer information 
     * @return {@link } no return Value
     */
  /*  public List<Trainer>  displayTrainerDetails() throws Exception {

        List<Trainer> showTrainer =  employeeServiceImpl.getTrainersFromDao();
        showTrainer.forEach(entry -> logger.info("Trainer id = "+entry.getId() +"\n"
                                                +"Trainer First Name : "+entry.getFirstName()+"\n"
                                                +"Trainer Blood Group : "+entry.getBloodGroup() +"\n"
                                                +"Trainer MobileNumber : "+entry.getMobileNumber()+"\n" 
                                                + "Trainer Email Id : "+entry.getEmail()+"\n"
                                                +"Trainer Aadhar Number :"+entry.getAadharNumber() +"\n"
                                                +"Trainer PanCard : "+entry.getPanCard()+"\n"
                                                +"************************************"));

    }*/

   /* public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        List<Trainer> showTrainer = null;
        try {
            showTrainer = employeeServiceImpl.getTrainersFromDao();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PrintWriter printWriter = response.getWriter();
        showTrainer.forEach(entry -> {

            printWriter.println("Trainer id : "+entry.getId() +"\n"
                  +"Trainer First Name : "+entry.getFirstName()+"\n"
                  +"Trainer Blood Group : "+entry.getBloodGroup() +"\n"
                  +"Trainer MobileNumber : "+entry.getMobileNumber()+"\n"
                  + "Trainer Email Id : "+entry.getEmail()+"\n"
                  +"Trainer Aadhar Number :"+entry.getAadharNumber() +"\n"
                  +"Trainer PanCard : "+entry.getPanCard()+"\n"
                  +"************************************");
        });
        //response.setStatus(200);
       // response.setHeader("Content-Type", "application/json");


    }*/
   /* public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String paramValue = request.getParameter("id");
        if (!paramValue.isEmpty()) {

                String uri = request.getRequestURI();
                List<Trainer> showTrainer = null;
                try {
                    showTrainer = employeeServiceImpl.getTrainersFromDao();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                PrintWriter printWriter = response.getWriter();
                showTrainer.forEach(entry -> {

                    printWriter.println("Trainer id : " + entry.getId() + "\n"
                            + "Trainer First Name : " + entry.getFirstName() + "\n"
                            + "Trainer Blood Group : " + entry.getBloodGroup() + "\n"
                            + "Trainer MobileNumber : " + entry.getMobileNumber() + "\n"
                            + "Trainer Email Id : " + entry.getEmail() + "\n"
                            + "Trainer Aadhar Number :" + entry.getAadharNumber() + "\n"
                            + "Trainer PanCard : " + entry.getPanCard() + "\n"
                            + "************************************");
                });
            } else {
            int id = 0;
            id = Integer.parseInt(paramValue);
                Trainer trainer = null;

            try {
                    trainer = employeeServiceImpl.searchTrainerDetailsById(id);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            if (trainer != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Trainer First Name   :" + trainer.getFirstName() + "\n")
                            .append("Trainer Email        :" + trainer.getEmail() + "\n")
                            .append("Trainer Mobile Number:" + trainer.getMobileNumber() + "\n")
                            .append("Trainer Blood Group  :" + trainer.getBloodGroup() + "\n\n");

                    for (int i = 0; i < trainer.getTraineeDetails().size(); i++) {

                        logger.info("{}", (String.format("%d ." + "Trainee Name :" + trainer.getTraineeDetails().get(i).getFirstName() + "\n"
                                + "Trainee Id :" + trainer.getTraineeDetails().get(i).getId() + "\n", i + 1)));
                    }
                    response.getOutputStream().println(String.valueOf(stringBuilder));

                } else {

                        logger.error("Invalid Employee ID");
                }
            }
    }*/



    /**
     * method is used to display the Trainee information 
     * @return {@link } no return value
     */
    public void displayTraineeDetails() throws Exception {
         
        List <Trainee> showTrainee =  employeeServiceImpl.getTraineesFromDao();
        showTrainee.forEach(entry -> logger.info("Trainee id = "+entry.getId() +"\n\n"+"Trainee First Name : "
                                                +entry.getFirstName()+"\n"
                                                +"Trainee Blood Group : "+entry.getBloodGroup() +"\n"                                                 
                                                +"Trainee MobileNumber : "+entry.getMobileNumber()+"\n" 
                                                +"Trainee Email Id : "+entry.getEmail()+"\n"
                                                +"Trainer Aadhar Number :"+entry.getAadharNumber() +"\n"
                                                +"Trainer PanCard : "+entry.getPanCard()+"\n"
                                                +"************************************"));
    }

    /**
     * method is used to validate name 
     * @param {@link Scanner}scanner object 
     * @return {@link String} name 
     */
    public String getName(Scanner scanner) {

        boolean isCheckName = true;
        String name = null;
    
        while (isCheckName) {
            name = scanner.next();
            if (EmployeeUtil.isValidateFirstName(name)) {
                isCheckName = false;
            } else {
                logger.error("Enter the valid Name");
            }
        }
        return name;
    }

    /**
     * method is used to validate Date 
     * @param {@link Scanner}scanner object 
     * @return {@link String} Date
     */
    public String getDate(Scanner scanner)  {

        boolean isCheckDateFormat = true;
        String date = null;

        while (isCheckDateFormat) {
                                
            try {                                                         
                date = scanner.next();
           
                if (EmployeeUtil.isValidDate(date)) {                                                           
                    isCheckDateFormat = false;
                } else {
                    logger.error(" invalid format");
                }
            } catch (Exception e) {          
                logger.error(" invalid date format"+ e);                         
            }                                           
        }
        return date;
    }

    /**
     * method is used to validate MobileNumber
     * @param {@link Scanner}scanner object 
     * @return {@link String}validatedMobileNumber 
     */
    public long getMobileNumber(Scanner scanner) {
                                              
        boolean isCheckMobileNumber = true;
        long validatedMobileNumber = 0;
                                                   
        while (isCheckMobileNumber) {
            logger.info("Enter the MobileNumber");
            String mobileNumber = scanner.next();
                                                         
            if (EmployeeUtil.isValidateMobileNumber(mobileNumber)) {
                validatedMobileNumber = Long.parseLong(mobileNumber);
                isCheckMobileNumber = false;
            } else {
                logger.error(" invalid MobileNumber");
            }
        }
        return validatedMobileNumber;
    }

    /**
     * method is used to validate Email
     * @param {@link Scanner}scanner object 
     * @return {@link String}email 
     */ 
    public String getEmail(Scanner scanner) {

        boolean isCheckEmail = true;
        String email = null;

        while (isCheckEmail) {
                    
            try { 
                logger.info("Enter the Email  ID");  
                email = scanner.next();
                boolean isValidateEmail = EmployeeUtil.isValidateEmail(email);
                                        
                if (isValidateEmail) {
                    isCheckEmail = false;
                }
            } catch (EmailMismatchException e) {                                                   
                logger.error("invalid" + e );
            }                                    
        }
        return email;
    }

    /**
     * method is used to validate AadharNumber
     * @param {@link Scanner}scanner object 
     * @return {@link String}aadharNumber 
     */
    public String getAadharNumber(Scanner scanner) {
    
        boolean isCheckAadharNumber = true; 
        String aadharNumber = null;

        while (isCheckAadharNumber) {
            logger.info("Enter the AadharNumber");
            aadharNumber = scanner.next();               
                        
            if (EmployeeUtil.isValidateAadharNumber(aadharNumber)) {                   
                isCheckAadharNumber = false;
            } else {
                logger.error(" invalid AadharNumber");
            }
        }
        return aadharNumber;
    }
 
    /**
     * method is used to validate PanCard
     * @param {@link Scanner}scanner object 
     * @return {@link String}PanCard
     */
    public String getPanCard(Scanner scanner) {

        boolean isCheckPanCard = true;
        String panCard = null;

        while (isCheckPanCard) {
            logger.info("Enter the PanCard");
            panCard = scanner.next();
                                                    
            if (EmployeeUtil.isValidatePanCard(panCard)) {
                isCheckPanCard= false;
            } else {
                logger.error(" invalid Pan Number");
            }
        }  
        return panCard;
    }

    /**
     * method is used to validate Blood Group
     * @param {@link Scanner}scanner object 
     * @return {@link String}Blood Group
     */
    public String getBloodGroup(Scanner scanner) {

        boolean isCheckBloodGroup = true;
        String bloodGroup = null;

        while (isCheckBloodGroup) {
            logger.info("Enter Your BloodGroup");  
            bloodGroup = scanner.next();
                                           
            if (EmployeeUtil.isValidateBloodGroup(bloodGroup)) {                   
                isCheckBloodGroup = false;
            } else {
                logger.error(" invalid Blood Group");
            }
        }
        return  bloodGroup;
    }    

    /**
     * method is used to get update Trainer information by the user
     * @param {@link Scanner}scanner object
     * @param {@link Trainer} updateTrainer object
     * @return {@link Trainer} updateTrainer object
     */
    public Trainer updateTrainerDetails(Scanner scanner ,Trainer updateTrainer) throws Exception {

        scanner.nextLine();
        boolean isCheckName = true;
        logger.info("Enter the Update name");
        String firstName = getName(scanner);
        if (!firstName.isEmpty()) {
                 
           while (isCheckName) {

                boolean isValidateFirstName = EmployeeUtil.isValidateFirstName(firstName);
                if (isValidateFirstName) {       
                    updateTrainer.setFirstName(firstName);     
                    isCheckName = false;
                } else {

                    logger.error(" invalid Name");
                    scanner.nextLine();
                }
            }
        }

        boolean isCheckLastName = true; 
        logger.info("Enter the LastName ");
        String lastName = scanner.nextLine();
        if (!lastName.isEmpty()) {

            while (isCheckLastName) {    
 
                boolean isValidateLastName = EmployeeUtil.isValidateLastName(lastName); 
                if (isValidateLastName) {          
   
                    updateTrainer.setLastName(lastName);
                    isCheckLastName = false;
                } else {

                    logger.error("Enter the valid LastName");
                    scanner.nextLine();
                }
            }
        }
        
        boolean isCheckMobileNumber = true;
        logger.info("Enter to update MobileNumber");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty()) {

            while (isCheckMobileNumber) {

                boolean isValidateMobileNumber = EmployeeUtil.isValidateMobileNumber(mobileNumber);   
                if (isValidateMobileNumber) {

                    long validatedMobileNumber = Long.parseLong(mobileNumber);
                    updateTrainer.setMobileNumber(validatedMobileNumber);
                    isCheckMobileNumber = false;
                } else {

                    logger.error(" invalid MobileNumber");
                    scanner.nextLine();
                }
            }
        }
  
        logger.info("Enter the Email");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {

            updateTrainer.setEmail(email);
        }
                       
        boolean isCheckAadharNumber = true;
        logger.info("Enter the AadharNumber");
        String aadharNumber = scanner.nextLine();
        if (!aadharNumber.isEmpty()) {

            while (isCheckAadharNumber) {

                boolean isValidateAadharNumber = EmployeeUtil.isValidateAadharNumber(aadharNumber);
                if (isValidateAadharNumber) {

                    long validatedAadharNumber = Long.parseLong(aadharNumber);
                    updateTrainer.setAadharNumber(validatedAadharNumber);
                    isCheckAadharNumber = false;
                } else {

                    logger.error(" invalid AadharNumber");
                    scanner.nextLine();
                }
            }
        }                
        boolean isCheckPanCard = true;
        logger.info("Enter the PanCard");
        String panCard = scanner.nextLine();
        if(!panCard.isEmpty()) {

            while (isCheckPanCard) {

                boolean isValidatePanCard = EmployeeUtil.isValidatePanCard(panCard);
                if (isValidatePanCard) {

                    updateTrainer.setPanCard(panCard);
                    isCheckPanCard= false;
                } else {

                    logger.info(" invalid Pan Number");
                    scanner.nextLine();
                }
            }
        }           
        boolean isCheckBloodGroup = true;
        logger.info("Enter Your BloodGroup");
        String bloodGroup = scanner.nextLine();
        if (!bloodGroup.isEmpty()) {

            while (isCheckBloodGroup) {

                boolean isValidateBloodGroup = EmployeeUtil.isValidateBloodGroup(bloodGroup);
                if (isValidateBloodGroup) {

                    updateTrainer.setBloodGroup(bloodGroup);
                    isCheckBloodGroup = false;
                } else {

                    logger.error(" invalid Blood Group");
                    scanner.nextLine();
                }
            }
        }	    
        return updateTrainer;                  
    }

    /**
     * method is used to get update Trainee information by the user
     * @param {@link Scanner}scanner object
     * @param {@link Trainee} updateTrainee Object
     * @return {@link Trainee} updateTrainee object
     */
    public Trainee updateTraineeDetails(Scanner scanner ,Trainee updateTrainee) throws Exception{
        scanner.nextLine();
        boolean isCheckName = true;
        logger.info("Enter the Update name");
        String firstName = scanner.nextLine();
        if (!firstName.isEmpty()) {

            while (isCheckName) { 

                boolean isValidateFirstName = EmployeeUtil.isValidateFirstName(firstName);
                if(isValidateFirstName) {  
              
                    updateTrainee.setFirstName(firstName); 
                    isCheckName = false;
                }else {

                    logger.error(" invalid Name");
                    scanner.nextLine();
                }
            }
        }
        boolean isCheckLastName = true;      
        logger.info("Enter the LastName ");
        String lastName = scanner.nextLine();
        if (!lastName.isEmpty()) {

            while (isCheckLastName) {

                boolean isValidateLastName = EmployeeUtil.isValidateLastName(lastName); 
                if (isValidateLastName) { 
            
                    updateTrainee.setLastName(lastName);
                    isCheckLastName = false;
                } else {

                    logger.error("Enter the valid LastName");
                    scanner.nextLine();
                }
            }
         }
        
        boolean isCheckMobileNumber = true;
        logger.info("Enter to update MobileNumber");
        String mobileNumber = scanner.nextLine();
        if (!mobileNumber.isEmpty()) {

            while (isCheckMobileNumber) {

                boolean isValidateMobileNumber = EmployeeUtil.isValidateMobileNumber(mobileNumber);   
                if (isValidateMobileNumber) {

                    long validatedMobileNumber = Long.parseLong(mobileNumber);
                    updateTrainee.setMobileNumber(validatedMobileNumber);
                    isCheckMobileNumber = false;
                } else {

                    logger.error(" invalid MobileNumber");
                    scanner.nextLine();
                }
            }
        }

        logger.info("Enter the Email");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {

            updateTrainee.setEmail(email);
        }
                       
        boolean isCheckAadharNumber = true;
        logger.info("Enter the AadharNumber");
        String aadharNumber = scanner.nextLine();
        if (!aadharNumber.isEmpty()) {

            while (isCheckAadharNumber) {

                boolean isValidateAadharNumber = EmployeeUtil.isValidateAadharNumber(aadharNumber);
                if (isValidateAadharNumber) {

                    long validatedAadharNumber = Long.parseLong(aadharNumber);
                    updateTrainee.setAadharNumber(validatedAadharNumber);
                    isCheckAadharNumber = false;
                } else {

                    logger.error(" invalid AadharNumber");
                    scanner.nextLine();
                    
                }
            }
        }                
        boolean isCheckPanCard = true;
        logger.info("Enter the PanCard");
        String panCard = scanner.nextLine();
        if (!panCard.isEmpty()) {

            while (isCheckPanCard) {

                boolean isValidatePanCard = EmployeeUtil.isValidatePanCard(panCard);
                if (isValidatePanCard) {

                    updateTrainee.setPanCard(panCard);
                    isCheckPanCard= false;
                } else {

                    logger.error(" invalid Pan Number");
                    scanner.nextLine();
                }
            }
        }           
        boolean isCheckBloodGroup = true;
        logger.info("Enter Your BloodGroup");
        String bloodGroup = scanner.nextLine();
        if (!bloodGroup.isEmpty()) {

            while (isCheckBloodGroup) {

                boolean isValidateBloodGroup = EmployeeUtil.isValidateBloodGroup(bloodGroup);
                if (isValidateBloodGroup) {

                    updateTrainee.setBloodGroup(bloodGroup);
                    isCheckBloodGroup = false;
                } else {

                    logger.error(" invalid Blood Group");
                    scanner.nextLine();
                }
            }
        }	    
        return updateTrainee;                  
    }  
    
    /**
     * method is used to Associate Trainees to Trainer
     * @param {@link Scanner}scanner object
     * @return {@link }
     */ 
     public void  assignTraineesToTrainer(Scanner scanner) throws Exception {

        try {
            List<Trainee> trainee = employeeServiceImpl.getTraineesFromDao();
            System.out.println("Enter the Trainer id");
            int trainerId = scanner.nextInt();                                    
            Trainer trainer = employeeServiceImpl.searchTrainerDetailsById(trainerId);

            if (trainer != null) { 

                displayTraineeDetails();  
                System.out.println("enter the Trainee id (ex: 1,2,3)");
                String[] traineeId = scanner.next().split(",");
                int id=0;

                for (int i = 0; i < traineeId.length; i++) {
                    id =Integer.valueOf(traineeId[i]);
     
                    for (Trainee retriveTrainee : trainee) {

                        if (retriveTrainee.getId() == id) {
                            trainer.getTraineeDetails().add(retriveTrainee);
                        } else {
                            System.out.println("no trainee");
                        }
                    }                
                }
                employeeServiceImpl.updatedTrainerDetails(trainerId , trainer);           
            } else {                                                                                             
                System.out.println("no trainer");
            }                                                                                                                                                                                                                                                                                                            
        } catch (NumberFormatException exception) {
            System.out.println("Enter the valid traineeID");
            throw exception;

        }       
    } 
                                                                                                                               
    /**
     * method is used to Associate Trainer to Trainee
     * @param {@link Scanner}scanner object
     * @return {@link }
     */
    public void  assignTrainersToTrainee(Scanner scanner) throws Exception {

        try {
            List<Trainer> trainer = employeeServiceImpl.getTrainersFromDao() ;   
            System.out.println("Enter the Trainee id");
            int traineeId = scanner.nextInt();                                   
            Trainee trainee = employeeServiceImpl.searchTraineeDetailsById(traineeId);
                                          
            if (trainee != null) {
                //displayTrainerDetails();
                System.out.println("enter the Trainer id (ex: 1,2,3)");
                String[] trainerId = scanner.next().split(",");
                int id=0;
                                                                 
                for (int i = 0; i < trainerId.length; i++) {
                    id =Integer.valueOf(trainerId[i]);  
                                           
                    for (Trainer retriveTrainer : trainer) {
                                              
                        if (retriveTrainer.getId() == id) {
                            trainee.getTrainerDetails().add(retriveTrainer);
                        } else {
                            System.out.println("no trainee");                        
                        }               
                    } 
                } 
                employeeServiceImpl.updatedTraineeDetails(traineeId , trainee);                   
            } else {                                                                                             
                System.out.println("no trainer" +traineeId);
            } 
        } catch (NumberFormatException exception ) {
            System.out.println("Enter the valid trainer  ID");
            throw exception;
        }         
    }
    public void unAssignTrainerToTrainee(Scanner scanner) throws Exception {

        System.out.println("Enter the trainerid ");
        int trainerId = scanner.nextInt();
        Trainer trainer = employeeServiceImpl.searchTrainerDetailsById(trainerId);             
        
        if (null != trainer) {
            List<Trainee> trainees = trainer.getTraineeDetails();
                                               
            for (int i = 0; i<trainees.size();i++) {
                                                                                                                                                                                                                                                                
                logger.info("{}","\n"+"Trainee Name : " +trainees.get(i).getFirstName()+"\n"
                                     +"Trainee Id : "+ trainees.get(i).getId()+"\n\n");
            }
            System.out.println("Enter the trainee id to unassign");
            int traineeId = scanner.nextInt();
                                                                                                                                                                         
            for (int i = 0; i<trainees.size(); i++) {
                                                                 
                if (trainer.getTraineeDetails().get(i).getId() == traineeId) {
                    trainees.remove(i);
                    boolean isUnAssighned = employeeServiceImpl.updatedTrainerDetails(trainerId , trainer);
                                                                          
                    if (isUnAssighned) {
                        logger.info("unassighned sucessfully");
                    } else {
                        logger.error("not unassighned");
                    }
                } else {
                    logger.error("Id not found");
                }
             }
        } else {
            logger.error("Trainer Id not found");
        }                                                                                                                                                                                                                                                                                            
    }
    public void unAssignTraineeToTrainer(Scanner scanner) throws Exception {

        System.out.println("Enter the traineeid ");
        int traineeId = scanner.nextInt();
        Trainee trainee = employeeServiceImpl.searchTraineeDetailsById(traineeId);

        if (null != trainee) {
            List<Trainer> trainers= trainee.getTrainerDetails();
            for (int i = 0; i<trainers.size();i++) {
                                                                                                                                                                                                                                                                
                logger.info("{}","\n"+"Trainer Name : " +trainers.get(i).getFirstName()+"\n"
                                     +"Trainer Id : "+ trainers.get(i).getId()+"\n\n");
            }
            System.out.println("Enter the trainer id to unassighn");
            int trainerId = scanner.nextInt();
                             
            for (int i = 0; i<trainers.size(); i++) {
                                                                                
                if (trainee.getTrainerDetails().get(i).getId() == trainerId) {
                    trainers.remove(i);
                    boolean isUnAssighned = employeeServiceImpl.updatedTraineeDetails(traineeId , trainee);
                                                  
                    if (isUnAssighned) {
                        System.out.println("unassighned sucessfully");
                    } else {
                        System.out.println("not unassighned");
                    }
                } else {
                    System.out.println("Id not found");
                }
            }
        } else {
            logger.error("Trainee Id not found");
        }                                                                                                                                                                                                                                                                                              
    }
}