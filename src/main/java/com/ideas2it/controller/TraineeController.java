package com.ideas2it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;
import com.ideas2it.service.impl.EmployeeServiceImpl;
import com.ideas2it.util.EmployeeUtil;
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




public class TraineeController extends HttpServlet {

     private static final Logger logger = LoggerFactory.getLogger(TrainerController.class);
    private static final EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
    ObjectMapper mapper = new ObjectMapper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uri = request.getRequestURI();
        PrintWriter printWriter = response.getWriter();

        if (uri.equals("/ServletExample/trainee")) {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);

            }
            String payload = buffer.toString();
            Map<String, String> map = new Gson().fromJson(payload, Map.class);
            boolean isCheckValidate = validateTraineeInformation(map, printWriter);
            Trainee trainee = null;

            trainee = mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .findAndRegisterModules().readValue(payload, Trainee.class);
            try {
                boolean isChecked;
                if (!isCheckValidate) {
                    isChecked = (employeeServiceImpl.addTrainee(trainee));
                    if (isChecked) {
                        printWriter.println(new Gson().toJson(" Insert Sucessfully"));
                        logger.info(" Insert Sucessfully");
                    } else {
                        printWriter.println(new Gson().toJson("not Inserted"));
                        logger.info(" Not Insert Sucessfully");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {

            printWriter.println(new Gson().toJson("Invalid URI"));
            logger.info(" Invalid URI");
        }
    }

    public boolean validateTraineeInformation(Map<String, String> map, PrintWriter printWriter) {
        boolean isCheckValidate = false;
        if (!EmployeeUtil.isValidateFirstName(map.get("firstName"))) {
            printWriter.println(new Gson().toJson("Enter the valid First Name"));
            logger.info("Enter the valid First Name");
            isCheckValidate = true;
        }
        if (!EmployeeUtil.isValidateLastName(map.get("lastName"))) {
            printWriter.println(new Gson().toJson("Enter the valid lastName"));
            logger.info("Enter the valid lastName");
            isCheckValidate = true;
        }
        if (!EmployeeUtil.isValidateAadharNumber(map.get("aadharNumber"))) {
            printWriter.println(new Gson().toJson("Enter the valid aadharNumber"));
            logger.info("Enter the valid aadharNumber");
            isCheckValidate = true;
        }
        if (!EmployeeUtil.isValidateBloodGroup(map.get("bloodGroup"))) {
            printWriter.println(new Gson().toJson("Enter the valid bloodGroup"));
            logger.info("Enter the valid bloodGroup");

            isCheckValidate = true;
        }
        if (!EmployeeUtil.isValidateEmail(map.get("email"))) {
            printWriter.println(new Gson().toJson("Enter the valid email id"));
            logger.info("Enter the valid email id");

            isCheckValidate = true;
        }

        if (!EmployeeUtil.isValidateMobileNumber(map.get("mobileNumber"))) {
            printWriter.println(new Gson().toJson("Enter the valid mobileNumber"));
            logger.info("Enter the valid email id");
            isCheckValidate = true;
        }
        if (!EmployeeUtil.isValidatePanCard(map.get("panCard"))) {
            printWriter.println(new Gson().toJson("Enter the valid pan Number"));
            logger.info("Enter the valid pan Number");
            isCheckValidate = true;
        }
        try {
            printWriter.println(EmployeeUtil.isValidDate(map.get("dateOfBirth")));
            if (!EmployeeUtil.isValidDate(map.get("dateOfBirth"))) {
                printWriter.println(new Gson().toJson("Enter the valid date(yyyy-mm-dd)"));
                logger.info("Enter the valid date(yyyy-mm-dd)");
                isCheckValidate = true;
            }
        } catch (Exception e) {
            printWriter.println(new Gson().toJson("Invalid format(yyyy-mm-dd)"));
            logger.info("Invalid format(yyyy-mm-dd)");
        }
        return isCheckValidate;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uri = request.getRequestURI();
        List<Trainee> showTrainee = null;
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            PrintWriter printWriter = response.getWriter();
            if (uri.equals("/ServletExample/trainees")) {

                try {
                    showTrainee = employeeServiceImpl.getTraineesFromDao();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                for (Trainee trainees : showTrainee) {
                    Map<String, Object> trainees1 = employeeServiceImpl.getTraineeObject(trainees);
                    printWriter.println(new Gson().toJson(trainees1));
                }
            } else if (uri.equals("/ServletExample/trainee")) {

                String paramValue = request.getParameter("id");
                int id = Integer.parseInt(paramValue);
                Trainee trainee = null;
                try {
                    trainee = employeeServiceImpl.searchTraineeDetailsById(id);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (trainee != null) {
                    Map<String, Object> trainees1 = employeeServiceImpl.getTraineeObject(trainee);
                    printWriter.println(new Gson().toJson(trainees1));
                }
                printWriter.println(new Gson().toJson(trainee));
            } else {
                printWriter.println(new Gson().toJson("Invalid Employee ID"));
                logger.info("Invalid Employee ID");
            }
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paramValue = request.getParameter("id");
        System.out.println(paramValue);
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
                logger.info("Deleted sucessfully");
            } else {
                printWriter.println(new Gson().toJson("not Deleted "));
                logger.info("not Deleted ");
            }
        } else {
            printWriter.println(" Trainer Id not found ");
            logger.info(new Gson().toJson(" Trainer Id not found "));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        PrintWriter printWriter = response.getWriter();
        if (uri.equals("/ServletExample/trainee")) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String payload = buffer.toString();
            System.out.println(payload);
            Trainee trainee = mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .findAndRegisterModules().readValue(payload, Trainee.class);
            int id = trainee.getId();
            try {

                boolean isChecked = (employeeServiceImpl.updatedTraineeDetails(id, trainee));
                if (isChecked) {
                    printWriter.println(new Gson().toJson("***** Updated Sucessfully *****"));
                    logger.info("***** Updated Sucessfully *****");
                } else {
                    printWriter.println(new Gson().toJson("not Inserted"));
                    logger.info("not Inserted");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (uri.equals("/ServletExample/assigntrainee")) {

            try {

                String paramValue = request.getParameter("traineeid");
                int traineeId = Integer.parseInt(paramValue);
                List<Trainer> trainer = employeeServiceImpl.getTrainersFromDao();

                try {
                    Trainee trainee = employeeServiceImpl.searchTraineeDetailsById(traineeId);

                    if (trainee != null) {

                        String paramValueTrainee = request.getParameter("trainerid");
                        String[] trainerId = paramValueTrainee.split(",");
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
                            printWriter.println(new Gson().toJson("Assigned Sucessfull"));
                            logger.info("Assigned Sucessfull");
                        } else {
                            printWriter.println(new Gson().toJson("notAssigned"));
                            logger.info("notAssigned");
                        }

                    } else {

                        printWriter.println(new Gson().toJson("no trainee"));
                        logger.info("no trainee");
                    }
                } catch (NumberFormatException exception) {
                    printWriter.println(new Gson().toJson("Enter the valid trainerID"));
                    logger.info("Enter the valid trainerID");
                    throw exception;

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            printWriter.println(new Gson().toJson("Invalid URI"));
            logger.info("Invalid URI");
        }
    }
}

