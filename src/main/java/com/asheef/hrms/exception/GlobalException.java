package com.asheef.hrms.exception;

import com.asheef.hrms.utils.ErrorStructure;
import com.asheef.hrms.utils.ResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@RestControllerAdvice
public class GlobalException {

    private List<ErrorStructure> mapFieldErrors(List<FieldError> fieldErrors) {
        List<ErrorStructure> errors = new ArrayList<>();

        fieldErrors.forEach(fieldError -> {
            Object rejectedValue = fieldError.getRejectedValue();

            errors.add(new ErrorStructure(
                    rejectedValue != null ? rejectedValue.toString() : null,
                    fieldError.getDefaultMessage(),
                    fieldError.getField()
            ));
        });
        return errors;
    }

    /**
     * Global Exception for RequestBody Dto Validation
     *
     * @param e
     * @return errors Structure
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleMethodValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
                new ResponseDto(Boolean.FALSE, HttpStatus.BAD_REQUEST.value(), mapFieldErrors(e.getFieldErrors()))
        );
    }

    /**
     * This is for handle Exception the dto Validation if the method accepting @ModelAttribute
     *
     * @param e
     * @return error Structure
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseDto> handleBindException(BindException e) {
        return ResponseEntity.badRequest().body(
                new ResponseDto(Boolean.FALSE, HttpStatus.BAD_REQUEST.value(), mapFieldErrors(e.getFieldErrors()))
        );
    }

    /**
     * This is for handle Exception for Constraint Violation
     *
     * @param ex
     * @return error Structure
     * @throws ConstraintViolationException
     */

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto> handleConstraintViolation(ConstraintViolationException ex) {
        var errors = new ArrayList<ErrorStructure>();

        ex.getConstraintViolations().forEach(violation -> {
            ErrorStructure errorStructure = new ErrorStructure(
                    String.valueOf(violation.getInvalidValue()),
                    violation.getMessage(),
                    violation.getPropertyPath().toString()
            );
            errors.add(errorStructure);
        });
        return ResponseEntity.badRequest().body(
                new ResponseDto(Boolean.FALSE, HttpStatus.BAD_REQUEST.value(), errors)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseDto(Boolean.FALSE, HttpStatus.BAD_REQUEST.value(), e.getMessage())
        );
    }

    /**
     * This is for handling JPA System Exceptions.
     * Example: Hibernate fails to extract a column value from the database.
     *
     * @param e the JPA system exception
     * @return error response containing the exception message and row information
     */
    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<ResponseDto> handleJpaSystemException(JpaSystemException e) {
        String message = e.getMostSpecificCause().getMessage();
        String row = null;

        if (e.getMessage() != null && e.getMessage().matches("(?s).*column \\[\\d+\\].*")) {
            Matcher matcher = java.util.regex.Pattern
                    .compile("column \\[(\\d+)\\]")
                    .matcher(e.getMessage());

            if (matcher.find())
                row = "row[" + matcher.group(1) + "]";
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseDto(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        message + (row != null ? " - " + row : ""))
        );
    }

    /**
     * This is for handle Exception for Data Access Exception
     * Example: An error occurs while performing a database operation.
     *
     * @param e
     * @return error Structure
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseDto> handleDataAccessException(DataAccessException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseDto(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        e.getMostSpecificCause().getMessage())
        );
    }

}
