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

import static com.ideas2it.controller.EmployeeController.employeeServiceImpl;


public class TraineeController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
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
            Trainee trainee= mapper.readValue(payload, Trainee.class);
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

        try {
            showTrainee = employeeServiceImpl.getTraineesFromDao();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(showTrainee != null) {
            PrintWriter printWriter = response.getWriter();
            showTrainee.forEach(entry -> printWriter.println("Trainee id : " + entry.getId() + "\n"
                    + "Trainer First Name : " + entry.getFirstName() + "\n"
                    + "Trainer Blood Group : " + entry.getBloodGroup() + "\n"
                    + "Trainer MobileNumber : " + entry.getMobileNumber() + "\n"
                    + "Trainer Email Id : " + entry.getEmail() + "\n"
                    + "Trainer Aadhar Number :" + entry.getAadharNumber() + "\n"
                    + "Trainer PanCard : " + entry.getPanCard() + "\n"
                    + "************************************"));
        }
        else {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("nodata");
        }
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paramValue = request.getParameter("id");
        int id = Integer.parseInt(paramValue);
        PrintWriter printWriter = response.getWriter();
        String message =" ";


        if (0 != id) {
            boolean isCheckedDelete;
            try {
                isCheckedDelete = employeeServiceImpl.deleteTraineeDetails(id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (isCheckedDelete) {
                printWriter.println("Deleted Trainee sucessfully");
            } else {
                printWriter.println("not Deleted ");
            }
        }
        else {
            printWriter.println(" Trainee Id not found ");
        }
    }

}

