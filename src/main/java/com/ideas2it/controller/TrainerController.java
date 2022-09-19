package com.ideas2it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import static java.lang.System.out;

public class TrainerController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Trainer.class);
    private static final EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
    ObjectMapper mapper = new ObjectMapper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String pathInfo = request.getPathInfo();
        String message = "*** Not Inserted ***";
        if (pathInfo == null || pathInfo.equals("/")) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            out.println(reader + "reader");

            String line;
            while ((line = reader.readLine()) != null) {
                out.println(buffer.append(line) + "insdie while");

            }

            String payload = buffer.toString();
            Trainer trainer = mapper.readValue(payload, Trainer.class);
            try {
                //validationOfInputs(trainer, response);
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String uri = request.getRequestURI();
        List<Trainer> showTrainer = null;
        if (uri.equals("/ServletExample/trainers")) {

            try {
                showTrainer = employeeServiceImpl.getTrainersFromDao();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            PrintWriter printWriter = response.getWriter();
            printWriter.print(showTrainer);


        } else {
            String paramValue = request.getParameter("id");
            out.println(paramValue);
            int id = Integer.parseInt(paramValue);
            PrintWriter printWriter = response.getWriter();


            Trainer trainer = null;

            try {
                trainer = employeeServiceImpl.searchTrainerDetailsById(id);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (trainer != null) {
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < trainer.getTraineeDetails().size(); i++) {

                    stringBuilder.append((String.format("%d ." + "Trainee Name :" + trainer.getTraineeDetails().get(i).getFirstName() + "\n"+
                            "Trainee Id :" + trainer.getTraineeDetails().get(i).getId() + "\n", i + 1)));
                     stringBuilder.append(trainer);
                }

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
        String message = "*** Not Inserted ***";
        if (pathInfo == null || pathInfo.equals("/")) {
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
            } else  {
                PrintWriter printWriter = response.getWriter();
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
                                    } else {
                                        out.println("no trainee");
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


            } //else {
                //response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                //response.getOutputStream().println(message);
            //}


        }
    }
}