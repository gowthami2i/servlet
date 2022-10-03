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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

@RestController
public class TraineeController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(TrainerController.class);
    public EmployeeService employeeServiceImpl;

    public TraineeController(EmployeeService employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @PostMapping(path = "/add_trainee")
    public String insertTrainee(@RequestBody Trainee trainee) throws Exception {
        boolean isChecked = employeeServiceImpl.addTrainee(trainee);
        if (isChecked) {
            return "Insert Succesfully";
        } else {
            return "Not Inserted";
        }
    }

    @GetMapping(path = "/trainees", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Trainee> getTrainee() throws Exception {
        List<Trainee> trainees = employeeServiceImpl.getTraineesFromDao();
        return trainees;
    }

    @GetMapping("/trainee/{id}")
    public Trainee getTraineeById(@PathVariable int id) throws Exception {
        int traineeId = id;
        Trainee getTrainee = null;
        getTrainee = employeeServiceImpl.searchTraineeDetailsById(traineeId);
        if (null != getTrainee) {
            return getTrainee;
        } else {
            return null;
        }
    }

    @PutMapping(path = "/update_trainee")
    public String updateTrainer(@RequestBody Trainee trainee) throws Exception {
        int traineeId = trainee.getId();
        Trainee updateTrainee = employeeServiceImpl.searchTraineeDetailsById(traineeId);
        if (null != updateTrainee) {
            boolean isChecked = employeeServiceImpl.updatedTraineeDetails(traineeId, trainee);
            if (isChecked) {
                return "Updated SuccessFully";
            } else {
                return "Not Updated";
            }
        } else {
            return "Id not found";
        }
    }

    @DeleteMapping(path = "/delete_trainee/{id}")
    public String deleteTrainee(@PathVariable int id) throws Exception {
        int traineeId = id;
        Trainee trainee = employeeServiceImpl.searchTraineeDetailsById(traineeId);
        if (null != trainee) {
            boolean isChecked = employeeServiceImpl.deleteTraineeDetails(traineeId);
            if (isChecked) {
                return "Deleted succesfully";
            } else {
                return "not deleted";
            }
        } else {
            return "id not found";
        }
    }

    @PutMapping("/assign_trainee/{traineeid}/{trainerid}")
    public String assigntrainer(@PathVariable int traineeid,
                                @PathVariable String trainerid) throws Exception {
        int id = traineeid;
        String id1 = trainerid;
        List<Trainer> list = null;
        list = employeeServiceImpl.getTrainersFromDao();
        Trainee trainee = employeeServiceImpl.searchTraineeDetailsById(id);

        if (trainee != null) {
            String[] trainersId = id1.split(",");
            int id2 = 0;

            boolean isChecked = false;
            for (int i = 0; i < trainersId.length; i++) {
                id2 = Integer.valueOf(trainersId[i]);

                for (Trainer retriveTrainee : list) {

                    if (retriveTrainee.getId() == id2) {
                        trainee.getTrainerDetails().add(retriveTrainee);
                    }
                    isChecked = employeeServiceImpl.updatedTraineeDetails(id, trainee);
                }
            }
            System.out.println("Ischecked : " + isChecked);
            if (isChecked) {
                return ("Assigned Successful");
            } else {
                return ("notAssigned");
            }

        } else {
            return ("no trainer");
        }
    }
    @PutMapping("/unassign_trainee/{traineeid}/{trainerid}")
    public String unAssignTrainer(@PathVariable int traineeid, @PathVariable int trainerid) throws Exception {
        int id = traineeid;
        Trainee trainee = employeeServiceImpl.searchTraineeDetailsById(id);
        if(null != trainee) {
            List<Trainer> trainer = trainee.getTrainerDetails();
            int id1 = trainerid;
            boolean isChecked = false;
            for (int i = 0; i < trainer.size(); i++) {
                if (trainee.getTrainerDetails().get(i).getId() == id1) {
                    trainer.remove(i);
                }
                isChecked = employeeServiceImpl.updatedTraineeDetails(id, trainee);
            }
            if (isChecked) {
                return ("UnAssigned Successful");
            } else {
                return ("notAssigned");
            }
        } else {
            return "no trainee";
        }
    }
}

