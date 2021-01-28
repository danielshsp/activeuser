package com.nice.activeuser.errorhandler;

import com.nice.activeuser.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

//Center of all rest error handler
@org.springframework.web.bind.annotation.ControllerAdvice(annotations = RestController.class)
public class ControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> constraintViolationException(final ConstraintViolationException exception, final WebRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setMessage(exception.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setUri(request.getContextPath());
        return ResponseEntity.badRequest().body(error);
    }

    //recommended to add  custom error handler and invoke them here
}
