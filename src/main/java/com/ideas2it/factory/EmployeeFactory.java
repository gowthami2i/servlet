package com.ideas2it.factory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideas2it.model.Trainer;
import com.ideas2it.model.Trainee;

/**
 * <h1>EmployeeUtill </h1>
 * Employee factory is an connection for the session factory.
 * 
 * @author  Gowtham P
 * @version java 1.0
 * 
 */

public class EmployeeFactory {

    private static SessionFactory factory;
    private static Logger logger = LoggerFactory.getLogger(EmployeeFactory.class);

    private EmployeeFactory() {

    }

    /**
     * method is used to create a session for connect database
     * @return {@link SessionFactory} factory object
     */
    public static SessionFactory getEmployeeFactory() {

        try {

            factory = new Configuration().
                             configure().
                             addPackage("com.ideas2it.model").
                             addAnnotatedClass(Trainer.class).
                             addAnnotatedClass(Trainee.class).buildSessionFactory();
        } catch (Throwable exception) {

            logger.error("Failed to create sessoionFactory object"+"{}", exception);
            throw new ExceptionInInitializerError(exception);
        }
        return factory;
    }
   

}


