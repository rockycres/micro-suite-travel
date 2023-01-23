package com.learn.ordersvc.exception;


import com.fasterxml.jackson.core.JsonParseException;

import com.learn.ordersvc.common.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerErrorException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


/**
 * Class that implements a handler of exceptions and errors in the API, using {@ControllerAdvice}
 * and sending the proper response to the client.
 *
 * @param <T>
 */
@ControllerAdvice
public class OrdersJavaAPIExceptionHandler<T> {

    /**
     * Method that handles with a TravelInvalidUpdateException and returns an error with
     * status code = 403.
     *
     * @param exception
     * @return ResponseEntity<Response < T>>
     */
    @ExceptionHandler(value = {OrderInvalidUpdateException.class})
    protected ResponseEntity<Response<T>> handleTravelInvalidUpdateException(OrderInvalidUpdateException exception) {

        Response<T> response = new Response<>();
        response.addErrorMsgToResponse(exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * Method that handles with a TravelNotFoundException and returns an error with
     * status code = 404.
     *
     * @param exception
     * @return ResponseEntity<Response < T>>
     */
    @ExceptionHandler(value = {OrderNotFoundException.class})
    protected ResponseEntity<Response<T>> handleTravelNotFoundException(OrderNotFoundException exception) {

        Response<T> response = new Response<>();
        response.addErrorMsgToResponse(exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Method that handles with a HttpClientErrorException and returns a Conflict
     * error with status code = 409.
     *
     * @param exception
     * @return ResponseEntity<Response < T>>
     */
    @ExceptionHandler(value = {HttpClientErrorException.Conflict.class})
    protected ResponseEntity<Response<T>> handleConflictException(HttpClientErrorException exception) {

        Response<T> response = new Response<>();
        response.addErrorMsgToResponse(exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Method that handles with a HttpMessageNotReadableException or JsonParseException and
     * returns an Unprocessable Entity error with status code = 422.
     *
     * @param exception
     * @return ResponseEntity<Response < T>>
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class, JsonParseException.class, NotParsableContentException.class})
    protected ResponseEntity<Response<T>> handleMessageNotReadableException(Exception exception) {

        Response<T> response = new Response<>();
        response.addErrorMsgToResponse(exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    /**
     * Method that handles with a HttpClientErrorException and returns a TooManyRequests error
     * with status code = 429.
     *
     * @param exception
     * @return ResponseEntity<Response < T>>
     */
    @ExceptionHandler(value = {HttpClientErrorException.TooManyRequests.class})
    protected ResponseEntity<Response<T>> handleTooManyRequestException(HttpClientErrorException exception) {

        Response<T> response = new Response<>();
        response.addErrorMsgToResponse(exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }

    /**
     * Method that handles with a TravelsJavaAPIException and returns an Internal Server Error
     * with status code = 500.
     *
     * @param exception
     * @return ResponseEntity<Response < T>>
     */
    @ExceptionHandler(value = {ServerErrorException.class})
    protected ResponseEntity<Response<T>> handleAPIException(ServerErrorException exception) {

        Response<T> response = new Response<>();
        response.addErrorMsgToResponse(exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    @ExceptionHandler(FeignClientException.class)
    public ResponseEntity<ResourceNotFoundDetails> handleResourceNotFoundException(FeignClientException rnfe ){
        return new ResponseEntity<>(
                ResourceNotFoundDetails.builder().timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("Resource Not Found")
                        .detail(rnfe.getMessage())
                        .developerMessage(rnfe.getClass().getName())
                        .build(), HttpStatus.NOT_FOUND);
    }




/*

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundDetails> handleResourceNotFoundException(ResourceNotFoundException rnfe ){
        return new ResponseEntity<>(
                ResourceNotFoundDetails.builder().timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("Resource Not Found")
                        .detail(rnfe.getMessage())
                        .developerMessage(rnfe.getClass().getName())
                        .build(), HttpStatus.NOT_FOUND);
    }



    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fields =  fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldsMsg =  fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(BAD_REQUEST.value())
                        .title("Field Validation Error")
                        .detail(ex.getMessage())
                        .developerMessage(ex.getClass().getName())
                        .fields(fields)
                        .fieldsMessage(fieldsMsg)
                        .build(), BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails=     ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(ex.getCause().getMessage())
                .detail(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();

        return new ResponseEntity(exceptionDetails, headers, status);
    }*/


}
