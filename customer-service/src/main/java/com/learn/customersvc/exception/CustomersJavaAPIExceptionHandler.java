package com.learn.customersvc.exception;


import com.fasterxml.jackson.core.JsonParseException;
import com.learn.customersvc.common.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerErrorException;


/**
 * Class that implements a handler of exceptions and errors in the API, using {@ControllerAdvice}
 * and sending the proper response to the client.
 *
 * @param <T>
 */
@ControllerAdvice
public class CustomersJavaAPIExceptionHandler<T> {

    /**
     * Method that handles with a TravelInvalidUpdateException and returns an error with
     * status code = 403.
     *
     * @param exception
     * @return ResponseEntity<Response < T>>
     */
    @ExceptionHandler(value = {CustomerInvalidUpdateException.class})
    protected ResponseEntity<Response<T>> handleAccountInvalidUpdateException(CustomerInvalidUpdateException exception) {

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
    @ExceptionHandler(value = {CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Response<T>> handleTravelNotFoundException(CustomerNotFoundException exception) {

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
    @ResponseStatus(HttpStatus.CONFLICT)
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
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
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
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
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

}
