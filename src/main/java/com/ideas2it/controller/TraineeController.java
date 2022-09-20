package com.ideas2it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;



public class TraineeController extends HttpServlet {

   // private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private static final EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
    ObjectMapper mapper = new ObjectMapper();

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String pathInfo = request.getPathInfo();
        String message = "";
        if (pathInfo == null || pathInfo.equals("/")) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String payload = buffer.toString();
            Trainee trainee = mapper.readValue(payload, Trainee.class);
            try {
                //validationOfInputs(trainer, response);
                boolean isChecked = employeeServiceImpl.addTrainee(trainee);
                if (isChecked) {
                    message = "Inserted suceesfully";
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


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        List<Trainee> showTrainee = null;
        PrintWriter printWriter = response.getWriter();

        try {
            showTrainee = employeeServiceImpl.getTraineesFromDao();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (showTrainee != null) {

            String jsonStr = mapper.writeValueAsString(showTrainee);
            printWriter.print(jsonStr);


        } else {
            printWriter.println("nodata");
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
                isCheckedDelete = employeeServiceImpl.deleteTraineeDetails(id);
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
        String message = "*** Not Inserted ***";
        if (pathInfo == null || pathInfo.equals("/")) {
            if (uri.equals("/ServletExample/trainee")) {

                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String payload = buffer.toString();
                Trainee trainee = mapper.readValue(payload, Trainee.class);
                int id = trainee.getId();
                try {

                    boolean isChecked = (employeeServiceImpl.updatedTraineeDetails(id, trainee));
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
                PrintWriter printWriter = response.getWriter();
                try {

                    String paramValue = request.getParameter("traineeid");
                    System.out.println(paramValue);
                    int traineeId = Integer.parseInt(paramValue);
                    List<Trainer> trainer = (List<Trainer>) employeeServiceImpl.getTrainersFromDao();

                    try {
                        Trainee trainee = employeeServiceImpl.searchTraineeDetailsById(traineeId);

                        if (trainee != null) {

                            String paramValueTrainer = request.getParameter("trainerid");
                            System.out.println(paramValueTrainer +"trainerid");
                            String[] trainerId = paramValueTrainer.split(",");
                            int id = 0;

                            for (int i = 0; i < trainerId.length; i++) {
                                id = Integer.valueOf(trainerId[i]);


                                for (Trainer retriveTrainer : trainer) {

                                    if (retriveTrainer.getId() == id) {
                                        trainee.getTrainerDetails().add(retriveTrainer);

                                    }
                                }
                            }
                            boolean isChecked = employeeServiceImpl.updatedTraineeDetails(traineeId, trainee);
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
            }
        }

    }
}