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
import java.util.Objects;

/**
 * <h1>Trainer Controller</h1>
 * The Trainer Controller class is used to get infromation from the user .
 *
 * @author Gowtham P
 * @version java 1.0
 */

public class TrainerController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(Trainer.class);
    private static final EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
    ObjectMapper mapper = new ObjectMapper();


    /**
     * method is used to to get a request a data the user
     *
     * @param {@link Scanner}scanner object
     * @param {@link EmployeeService} employeeServiceImpl Object
     * @return {@link Trainer} trainer object
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uri = request.getRequestURI();
        PrintWriter printWriter = response.getWriter();

        if (uri.equals("/ServletExample/trainers")) {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);

            }
            String payload = buffer.toString();
            Map<String, String> map = new Gson().fromJson(payload, Map.class);
            boolean isCheckValidate = validateTrainerInformation(map, printWriter);
            Trainer trainer = null;

            trainer = mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .findAndRegisterModules().readValue(payload, Trainer.class);
            try {
                boolean isChecked;
                if (!isCheckValidate) {
                    isChecked = (employeeServiceImpl.addTrainer(trainer));
                    if (isChecked) {
                        printWriter.println(new Gson().toJson(" Insert Sucessfully"));
                        logger.info(" Insert Sucessfully");
                    } else {
                        printWriter.println(new Gson().toJson("not Inserted"));
                        logger.info("not Inserted");

                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {

            printWriter.println(new Gson().toJson("Invalid URI"));
            logger.info("Invalid URI");
        }

    }

    public boolean validateTrainerInformation(Map<String, String> map, PrintWriter printWriter) {
        boolean isCheckValidate = false;
        if (!EmployeeUtil.isValidateFirstName(map.get("firstName"))) {
            printWriter.println(new Gson().toJson("Enter the valid First Name"));
            isCheckValidate = true;
        }
        if (!EmployeeUtil.isValidateLastName(map.get("lastName"))) {
            printWriter.println(new Gson().toJson("Enter the valid lastName"));
            isCheckValidate = true;
        }
        if (!EmployeeUtil.isValidateAadharNumber(map.get("aadharNumber"))) {
            printWriter.println(new Gson().toJson("Enter the valid aadharNumber"));
            isCheckValidate = true;
        }
        if (!EmployeeUtil.isValidateBloodGroup(map.get("bloodGroup"))) {
            printWriter.println(new Gson().toJson("Enter the valid bloodGroup"));
            isCheckValidate = true;
        }
        if (!EmployeeUtil.isValidateEmail(map.get("email"))) {
            printWriter.println(new Gson().toJson("Enter the valid email id"));
            isCheckValidate = true;
        }

        if (!EmployeeUtil.isValidateMobileNumber(map.get("mobileNumber"))) {
            printWriter.println(new Gson().toJson("Enter the valid mobileNumber"));
            isCheckValidate = true;
        }
        if (!EmployeeUtil.isValidatePanCard(map.get("panCard"))) {
            printWriter.println(new Gson().toJson("Enter the valid pan Number"));
            isCheckValidate = true;
        }
        try {
            printWriter.println(EmployeeUtil.isValidDate(map.get("dateOfBirth")));
            if (!EmployeeUtil.isValidDate(map.get("dateOfBirth"))) {
                printWriter.println(new Gson().toJson("Enter the valid date(yyyy-mm-dd)"));
                isCheckValidate = true;
            }

            if (!EmployeeUtil.isValidDate(map.get("dateOfJoinning"))) {
                printWriter.println(new Gson().toJson("Enter the valid dateOfjoinning (yyyy-mm-dd)"));
                isCheckValidate = true;
            }
        } catch (Exception e) {
            printWriter.println(new Gson().toJson("Invalid format(yyyy-mm-dd)"));
        }
        return isCheckValidate;
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
            for (Trainer trainers : showTrainer) {
                Map<String, Object> trainers1 = employeeServiceImpl.getTrainerObject(trainers);

                printWriter.println(new Gson().toJson(trainers1));
            }


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

                Map<String, Object> trainer1 = employeeServiceImpl.getTrainerObject(trainer);
                printWriter.println(new Gson().toJson(trainer1));

            } else {
                printWriter.println(new Gson().toJson("Invalid Employee ID"));
                logger.info("Invalid Employee ID");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        PrintWriter printWriter = response.getWriter();
        String message = " ";
        if (uri.equals("/ServletExample/trainer")) {
            String paramValue = request.getParameter("id");
            int trainerId = Integer.parseInt(paramValue);
            if (0 != trainerId) {
                boolean isCheckedDelete;
                try {
                    isCheckedDelete = employeeServiceImpl.deleteTrainerDetails(trainerId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (isCheckedDelete) {
                    printWriter.println(new Gson().toJson("Deleted sucessfully"));
                    logger.info("Deleted sucessfully");
                } else {
                    printWriter.println(new Gson().toJson("not Deleted "));
                    logger.info("not Deleted ");
                }
            } else {
                printWriter.println(new Gson().toJson("Invalid id"));
            }
        } else if (uri.equals("/ServletExample/unassigntrainer")) {
            try {

                String paramValue1 = request.getParameter("trainerid");
                int id = Integer.parseInt(paramValue1);
                try {
                    Trainer trainer = employeeServiceImpl.searchTrainerDetailsById(id);

                    if (trainer != null) {
                        List<Trainee> trainee = trainer.getTraineeDetails();
                        String paramValueTrainee = request.getParameter("traineeid");
                        int id1 = Integer.parseInt(paramValueTrainee);

                        for (int i = 0; i <= trainee.size(); i++) {
                            if (trainer.getTraineeDetails().get(i).getId() == id1) {
                                trainee.remove(i);
                                boolean isChecked = employeeServiceImpl.updatedTrainerDetails(id, trainer);
                                if (isChecked) {
                                    printWriter.println(new Gson().toJson("UnAssigned Sucessfull"));
                                    logger.info("UnAssigned Sucessfull");
                                } else {
                                    printWriter.println(new Gson().toJson("not UnAssigned"));
                                    logger.info("notAssigned");
                                }
                            }
                        }

                    } else {
                        printWriter.println(new Gson().toJson("no trainer"));
                        logger.info("no trainer");
                    }
                } catch (NumberFormatException exception) {
                    printWriter.println(new Gson().toJson("Enter the valid traineeID"));
                    logger.info("Enter the valid traineeID");
                    throw exception;

                }
            } catch (Exception e) {
                System.out.println("no data"+e);
            }

        } else {
            printWriter.println(new Gson().toJson("Invalid URI"));
            logger.info("Invalid URI");
        }
    }



    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        PrintWriter printWriter = response.getWriter();
        if (uri.equals("/ServletExample/trainer")) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String payload = buffer.toString();
            Map<String, String> map = new Gson().fromJson(payload, Map.class);
            boolean isCheckValidate = validateTrainerInformation(map, printWriter);
            Trainer trainer = null;
            trainer = mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .findAndRegisterModules().readValue(payload, Trainer.class);

            int id = trainer.getId();
            try {
                if (!isCheckValidate) {
                    boolean isChecked = (employeeServiceImpl.updatedTrainerDetails(id, trainer));

                    if (isChecked) {
                        printWriter.println(new Gson().toJson("***** Updated Sucessfully *****"));
                        logger.info("***** Updated Sucessfully *****");
                    } else {
                        printWriter.println(new Gson().toJson("not Inserted"));
                        logger.info("not Inserted");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if (Objects.equals(uri, "/ServletExample/assigntrainer")) {

            String paramValue = request.getParameter("trainerid");
            int trainerId = Integer.parseInt(paramValue);
            String id1 = request.getParameter("traineeid");
            List<Trainee> list = null;

            try {
                list = employeeServiceImpl.getTraineesFromDao();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                Trainer trainer = employeeServiceImpl.searchTrainerDetailsById(trainerId);

                if (trainer != null) {
                    String[] traineesId = id1.split(",");
                    int id = 0;

                    boolean isChecked = false;
                    for (int i = 0; i <traineesId.length; i++) {
                        id = Integer.valueOf(traineesId[i]);

                            for (Trainee retriveTrainee : list) {

                                if (retriveTrainee.getId() == id) {
                                    trainer.getTraineeDetails().add(retriveTrainee);
                                }
                                isChecked = employeeServiceImpl.updatedTrainerDetails(trainerId, trainer);
                            }
                        }
                        System.out.println("Ischecked : "+isChecked);
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
                    throw exception;

                } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            printWriter.println(new Gson().toJson("Invalid URI"));
            logger.info("Invalid URI");
        }
    }
}
