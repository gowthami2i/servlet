package com.ideas2it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.ideas2it.model.Employee;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <h1>Trainer Controller</h1>
 * The Trainer Controller class is used to get infromation from the user .
 *
 * @author  Gowtham P
 * @version java 1.0
 *
 */

public class TrainerController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Trainer.class);
    private static final EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
    ObjectMapper mapper = new ObjectMapper();


    /**
     * method is used to to get a request a data the user
     * @param {@link Scanner}scanner object 
     * @param {@link EmployeeService} employeeServiceImpl Object
     * @return {@link Trainer} trainer object
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String pathInfo = request.getPathInfo();
        String message = "*** Not Inserted ***";
        if (pathInfo == null || pathInfo.equals("/")) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line) ;

            }
            String payload = buffer.toString();
            Trainer trainer = mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                                .findAndRegisterModules().readValue(payload, Trainer.class);
            try {
                boolean isChecked = (employeeServiceImpl.addTrainer(trainer));
                if (isChecked) {
                    message = "***** Insert Sucessfully *****";
                } else {
                    message = "not Inserted";
                }
                response.getOutputStream().println(message);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().println(message);
        }
    }

   /* public  void validateTrainerInformation(Trainer trainer) {
        String name = trainer.getFirstName();
        trainer.getLastName();
        trainer.getEmail();
        trainer.getAadharNumber();
        trainer.getDateOfBirth();
        trainer.getDateOfJoinning();
        trainer.getMobileNumber();
        trainer.getPanCard();

        getName(trainer.getFirstName(name));
    }*/

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String uri = request.getRequestURI();
        List<Trainer> showTrainer = null;
        if (uri.equals("/ServletExample/trainers")) {

            try {
                showTrainer = (List<Trainer>) employeeServiceImpl.getTrainersFromDao();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            PrintWriter printWriter = response.getWriter();
            for(Trainer trainers : showTrainer) {
                Map<String, Object> trainers1 = employeeServiceImpl.getObject(trainers);

                printWriter.println( new Gson().toJson(trainers1));
            }
            String jsonStr = mapper.writeValueAsString(showTrainer);
            printWriter.print(jsonStr);

        } else {
            String paramValue = request.getParameter("id");
            int id = Integer.parseInt(paramValue);
            PrintWriter printWriter = response.getWriter();
            Trainer trainer = null;
            try {
                trainer = employeeServiceImpl.searchTrainerDetailsById(id);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (trainer != null) {

                printWriter.println( new Gson().toJson(trainer));
            } else {
                logger.error("Invalid Employee ID");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paramValue = request.getParameter("id");
        int id = Integer.parseInt(paramValue);
        PrintWriter printWriter = response.getWriter();
        String message = " ";


        if (0 != id) {
            boolean isCheckedDelete;
            try {
                isCheckedDelete = employeeServiceImpl.deleteTrainerDetails(id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (isCheckedDelete) {
                printWriter.println("Deleted sucessfully");
            } else {
                printWriter.println("not Deleted ");
            }
        } else {
            printWriter.println(" Trainer Id not found ");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            PrintWriter printWriter = response.getWriter();
            if (uri.equals("/ServletExample/trainer")) {

                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String payload = buffer.toString();
                Trainer trainer = mapper.readValue(payload, Trainer.class);
                int id = trainer.getId();
                try {

                    boolean isChecked = (employeeServiceImpl.updatedTrainerDetails(id, trainer));
                    if (!isChecked) {
                        printWriter.println("***** Insert Sucessfully *****");
                    } else {
                        printWriter.println("not Inserted");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (uri.equals("/ServletExample/assigntrainer")) {


                try {

                    String paramValue = request.getParameter("trainerid");
                    int trainerId = Integer.parseInt(paramValue);
                    List<Trainee> trainee = employeeServiceImpl.getTraineesFromDao();

                    try {
                        Trainer trainer = employeeServiceImpl.searchTrainerDetailsById(trainerId);

                        if (trainer != null) {

                            String paramValueTrainee = request.getParameter("traineeid");
                            String[] traineeId = paramValueTrainee.split(",");
                            int id = 0;

                            for (int i = 0; i < traineeId.length; i++) {
                                id = Integer.valueOf(traineeId[i]);


                                for (Trainee retriveTrainee : trainee) {

                                    if (retriveTrainee.getId() == id) {
                                        trainer.getTraineeDetails().add(retriveTrainee);
                                    }

                                }
                            }
                            boolean isChecked = employeeServiceImpl.updatedTrainerDetails(trainerId, trainer);
                            if (isChecked) {
                                printWriter.println("Assigned Sucessfull");
                            } else {
                                printWriter.println("notAssigned");
                            }

                        } else {

                            printWriter.println("no trainer");
                        }
                    } catch (NumberFormatException exception) {
                        printWriter.println("Enter the valid traineeID");
                        throw exception;

                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            } else {

                printWriter.println("Invalid URI");
            }
        }

    }
}