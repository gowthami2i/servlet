package com.ideas2it.controller;

import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import com.ideas2it.service.EmployeeService;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>Trainer Controller</h1>
 * The Trainer Controller class is used to get infromation from the user .
 *
 * @author Gowtham P
 * @version java 1.0
 */
@RestController
public class TrainerController extends HttpServlet {

    public EmployeeService employeeServiceImpl;

    public TrainerController(EmployeeService employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    private static final Logger logger = LoggerFactory.getLogger(Trainer.class);

    /**
     * method is used to get a request a data the user
     *
     * @param {@link Scanner}scanner object
     * @param {@link EmployeeService} employeeServiceImpl Object
     * @return {@link Trainer} trainer object
     */
    @PostMapping("/add_trainer")
    public String insertTrainer(@RequestBody Trainer trainer) throws Exception {
        boolean isChecked = (employeeServiceImpl.addTrainer(trainer));
        if (isChecked) {
            return "Insert Successfully";
        } else {
            return "not Inserted";
        }
    }

    @GetMapping(path = "/trainers", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public List<Map<String,Object>> getTrainer() throws Exception {
        List<Map<String,Object>> trainerList = new ArrayList<>();
        List<Trainer> trainers = employeeServiceImpl.getTrainersFromDao();
        for(Trainer trainer:trainers) {
            Map<String,Object> trainer1 = employeeServiceImpl.getTrainerObject(trainer);
            trainerList.add(trainer1);
        }

        if (null != trainerList) {
            return trainerList;
        } else {
            return null;
        }
    }

    @GetMapping("/trainer/{id}")
    public  Map<String,Object> getTrainerById(@PathVariable int id) throws Exception {
        int trainerId = id;

        Trainer trainer = employeeServiceImpl.searchTrainerDetailsById(trainerId);
        if(null != trainer) {
            Map<String, Object> getTrainer = employeeServiceImpl.getTrainerObject(trainer);
            if (null != getTrainer) {
                return getTrainer;
            } else {
                return null;
            }
        } else{
            return null;
        }
    }

    @PutMapping("/update_trainer")
    public String updateTrainer(@RequestBody Trainer trainer) throws Exception {
        int trainerId = trainer.getId();
        Trainer getTrainer = employeeServiceImpl.searchTrainerDetailsById(trainerId);
        boolean isChecked;
        if (null != getTrainer) {
            isChecked = employeeServiceImpl.updatedTrainerDetails(trainer);
            if (isChecked) {
                return "Updated SuccessFully";
            } else {
                return "Not Updated";
            }
        } else {
            return "TrainerId not found";
        }
    }

    @DeleteMapping("/delete_trainer/{id}")
    public String deleteTrainer(@PathVariable int id) throws Exception {
        int trainerId = id;
        Trainer deleteTrainer = employeeServiceImpl.searchTrainerDetailsById(trainerId);
        if (null != deleteTrainer) {
            boolean isChecked = employeeServiceImpl.deleteTrainerDetails(trainerId);

            if (isChecked) {
                return "Deleted succesfully";
            } else {
                return "not deleted";
            }
        } else {
            return "id not found";
        }
    }

    @PutMapping("/assign_trainer/{trainerid}/{traineeid}")
    public String assigntrainer(@PathVariable int trainerid,
                                @PathVariable String traineeid) throws Exception {
        int id = trainerid;
        String id1 = traineeid;
        List<Trainee> list = employeeServiceImpl.getTraineesFromDao();
        Trainer trainer = employeeServiceImpl.searchTrainerDetailsById(id);
        if (trainer != null) {
            String[] traineesId = id1.split(",");
            int id2 = 0;

            boolean isChecked = false;
            for (int i = 0; i < traineesId.length; i++) {
                id2 = Integer.valueOf(traineesId[i]);

                for (Trainee retriveTrainee : list) {

                    if (retriveTrainee.getId() == id2) {
                        trainer.getTraineeDetails().add(retriveTrainee);
                    }
                    isChecked = employeeServiceImpl.updatedTrainerDetails( trainer);
                }
            }
            if (isChecked) {
                return ("Assigned Successful");
            } else {
                return ("notAssigned");
            }

        } else {
            return ("no trainee");
        }

    }

    @PutMapping("/unassign_trainer/{trainerid}/{traineeid}")
    public String unAssignTrainer(@PathVariable int trainerid, @PathVariable int traineeid) throws Exception {
        int id = trainerid;
        Trainer trainer = employeeServiceImpl.searchTrainerDetailsById(id);
        if(null != trainer) {
            List<Trainee> trainee = trainer.getTraineeDetails();
            int id1 = traineeid;
            boolean isChecked = false;
            for (int i = 0; i < trainee.size(); i++) {
                if (trainee.get(i).getId() == id1) {
                    trainee.remove(i);
                }
                isChecked = employeeServiceImpl.updatedTrainerDetails( trainer);
            }
            if (isChecked) {
                return ("UnAssigned Successful");
            } else {
                return ("notAssigned");
            }
        } else {
            return "no trainer";
        }
    }
}
