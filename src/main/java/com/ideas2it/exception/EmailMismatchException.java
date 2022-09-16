package com.ideas2it.exception;

/**
* <h1>EmailMismatchException</h1>
*
* The EmailMismatchException class is used to Create a Exception for Email
* @author  Gowtham P
* @version java 1.0
* 
*/

public class EmailMismatchException extends Exception {
    
    /**
     * method is used Validate EmailMismatchException.
     * @return {@link String } email
     */
    public EmailMismatchException(String email) {
        super(email);
    }
}