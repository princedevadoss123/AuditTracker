package com.example.audittracker.exception;

/**
 * 
 * @author vmudigal
 *
 */
public class EmployeeNotFoundException extends BaseException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
