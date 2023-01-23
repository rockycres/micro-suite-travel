package com.learn.customersvc.controller;



import com.learn.customersvc.common.response.Response;
import com.learn.customersvc.common.util.CustomersApiUtil;
import com.learn.customersvc.dto.model.CustomerDTO;
import com.learn.customersvc.exception.CustomerInvalidUpdateException;
import com.learn.customersvc.exception.CustomerNotFoundException;
import com.learn.customersvc.exception.NotParsableContentException;
import com.learn.customersvc.model.Customer;
import com.learn.customersvc.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@RestController
@RequestMapping("/api-customer/v1/customer")
public class CustomerController {

    CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Method that creates customers in the database.
     *
     * @param apiVersion - API version at the moment
     * @param apiKey     - API Key to access the routes
     * @param dto,       where:
     *                   - id - trip id;
     *                   - orderNumber - identification number of a trip in the system;
     *                   - amount – customer amount; a string of arbitrary length that is parsable as a BigDecimal;
     *                   - startDate – initial date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone;
     *                   - endDate – final date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone;
     *                   - type - trip type: RETURN (with a date to begin and end), ONE_WAY (only with initial date),
     *                   MULTI_CITY (with multiple destinations);
     *                   - account_id - account id of the user in the API.
     * @param result     - Bind result
     * @return ResponseEntity with a <code>Response<CustomerDTO></code> object and the HTTP status
     * <p>
     * HTTP Status:
     * <p>
     * 201 - Created: Everything worked as expected.
     * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
     * 404 - Not Found: The requested resource doesn't exist.
     * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
     * 422 – Unprocessable Entity: if any of the fields are not parsable or the start date is greater than end date.
     * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
     * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
     * @throws NotParsableContentException
     *
     */
    @PostMapping
    @Operation(summary  = "Route to create customers")
    public ResponseEntity<Response<CustomerDTO>> createCustomer(@RequestHeader(value = CustomersApiUtil.HEADER_CUSTOMERS_API_VERSION, defaultValue = "${api.version}") @Parameter(description = " api version for this endpoint ") String apiVersion,
                                                        @RequestHeader(value = CustomersApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey, @Valid @RequestBody CustomerDTO dto, BindingResult result)
            throws NotParsableContentException {

        Response<CustomerDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Customer customer = dto.convertDTOToEntity();
        Customer customerToCreate = customerService.save(customer);

        CustomerDTO dtoSaved = customerToCreate.convertEntityToDTO();


        response.setData(dtoSaved);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(CustomersApiUtil.HEADER_CUSTOMERS_API_VERSION, apiVersion);
        headers.add(CustomersApiUtil.HEADER_API_KEY, apiKey);
        log.info("Create Customer Service Executed successfully",dtoSaved.getCustomerId());

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    /**
     * Method that updates customers in the database.
     *
     * @param apiVersion - API version at the moment
     * @param apiKey     - API Key to access the routes
     * @param dto,       where:
     *                   - id - trip id;
     *                   - orderNumber - identification number of a trip in the system;
     *                   - amount – customer amount; a string of arbitrary length that is parsable as a BigDecimal;
     *                   - initialDate – initial date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone;
     *                   - finalDate – final date time in the ISO 8601 format YYYY-MM-DDThh:mm:ss.sssZ in the Local time zone;
     *                   - type - trip type: RETURN (with a date to begin and end), ONE_WAY (only with initial date),
     *                   MULTI_CITY (with multiple destinations);
     *                   - account_id - account id of the user in the API.
     * @param result     - Bind result
     * @return ResponseEntity with a <code>Response<CustomerDTO></code> object and the HTTP status
     * <p>
     * HTTP Status:
     * <p>
     * 200 - OK: Everything worked as expected.
     * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
     * 404 - Not Found: The requested resource doesn't exist.
     * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
     * 422 – Unprocessable Entity: if any of the fields are not parsable or the start date is greater than end date.
     * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
     * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
     * @throws CustomerNotFoundException
     * @throws CustomerInvalidUpdateException
     * @throws NotParsableContentException
     * @author Mariana Azevedo
     * @since 02/04/2020
     */
    @PutMapping(path = "/{id}")
    @Operation(summary  = "Route to update a customer")
    public ResponseEntity<Response<CustomerDTO>> updateCustomer(@RequestHeader(value = CustomersApiUtil.HEADER_CUSTOMERS_API_VERSION, defaultValue = "${api.version}") String apiVersion,
                                                      @RequestHeader(value = CustomersApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey, @Valid @RequestBody CustomerDTO dto, BindingResult result)
            throws CustomerNotFoundException, CustomerInvalidUpdateException, NotParsableContentException {

        Response<CustomerDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }


        Customer customerToFind = customerService.findById(dto.getCustomerId());
        if (customerToFind.getCustomerId().compareTo(dto.getCustomerId()) != 0) {
            throw new CustomerInvalidUpdateException("You don't have permission to change the customer id=" + dto.getCustomerId());
        }

        Customer customer = dto.convertDTOToEntity();
        Customer customerToUpdate = customerService.save(customer);

        CustomerDTO itemDTO = customerToUpdate.convertEntityToDTO();
        response.setData(itemDTO);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(CustomersApiUtil.HEADER_CUSTOMERS_API_VERSION, apiVersion);
        headers.add(CustomersApiUtil.HEADER_API_KEY, apiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }



    /**
     * Method that search a customer by the id.
     *
     * @param apiVersion - API version at the moment
     * @param apiKey     - API Key to access the routes
     * @param customerId   - the id of the customer
     * @return ResponseEntity with a <code>Response<CustomerDTO></code> object and the HTTP status
     * <p>
     * HTTP Status:
     * <p>
     * 200 - OK: Everything worked as expected.
     * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
     * 404 - Not Found: The requested resource doesn't exist.
     * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
     * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
     * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
     * @throws CustomerNotFoundException
     */
    @GetMapping(value = "/{id}")
    @Operation(summary  = "Route to find a customer by your id in the API")
    public ResponseEntity<Response<CustomerDTO>> findById(
            @RequestHeader(value = CustomersApiUtil.HEADER_CUSTOMERS_API_VERSION, defaultValue = "${api.version}") String apiVersion,
            @RequestHeader(value = CustomersApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey,
            @PathVariable("id") Long customerId )
            throws CustomerNotFoundException {

        Response<CustomerDTO> response = new Response<>();
        Customer customer = customerService.findById(customerId);

        CustomerDTO dto = customer.convertEntityToDTO();



        response.setData(dto);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(CustomersApiUtil.HEADER_CUSTOMERS_API_VERSION, apiVersion);
        headers.add(CustomersApiUtil.HEADER_API_KEY, apiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    /**
     * Method that delete a unique trip.
     *
     * @param apiVersion - API version at the moment
     * @param apiKey     - API Key to access the routes
     * @param customerId   - the id of the customer
     * @return ResponseEntity with a <code>Response<String></code> object and the HTTP status
     * <p>
     * HTTP Status:
     * <p>
     * 204 - OK: Everything worked as expected.
     * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
     * 404 - Not Found: The requested resource doesn't exist.
     * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
     * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
     * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
     * @throws CustomerNotFoundException
     * @author Mariana Azevedo
     * @since 02/04/2020
     */
    @DeleteMapping(value = "/{id}")
    @Operation(summary  = "Route to delete a trip in the API")
    public ResponseEntity<Response<String>> deleteCustomer(@RequestHeader(value = CustomersApiUtil.HEADER_CUSTOMERS_API_VERSION, defaultValue = "${api.version}")
                                                   String apiVersion, @RequestHeader(value = CustomersApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey,
                                                   @PathVariable("id") Long customerId) throws CustomerNotFoundException {

        Response<String> response = new Response<>();
        Customer customer = customerService.findById(customerId);

        customerService.deleteById(customer.getCustomerId());
        response.setData("Customer id=" + customer.getCustomerId() + " successfully deleted");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(CustomersApiUtil.HEADER_CUSTOMERS_API_VERSION, apiVersion);
        headers.add(CustomersApiUtil.HEADER_API_KEY, apiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.NO_CONTENT);
    }


    /**
     * Method that search for all the customers saved in a period of time.
     *
     * @param apiVersion - API version at the moment
     * @param apiKey     - API Key to access the routes
     * @param startDate  - the start date of the search
     * @param endDate    - the end date of the search
     * @param pageable   Object for pagination information: the page that will be return in the search,
     *                   the size of page, and sort direction that the results should be shown: ASC - ascending order;
     *                   DESC - descending order.
     * @return ResponseEntity with a <code>Response<Page<CustomerDTO>></code> object and the HTTP status
     * <p>
     * HTTP Status:
     * <p>
     * 200 - OK: Everything worked as expected.
     * 404 - Not Found: The requested resource doesn't exist.
     * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
     * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
     * @throws CustomerNotFoundException
     * @author Mariana Azevedo
     * @since 02/04/2020
     */
   /* @GetMapping
    @Operation(summary  = "Route to find all customers of the API in a period of time")
    public ResponseEntity<Response<List<CustomerDTO>>> findAllBetweenDates(@RequestHeader(value = CustomersApiUtil.HEADER_CUSTOMERS_API_VERSION, defaultValue = "${api.version}")
                                                                           String apiVersion, @RequestHeader(value = CustomersApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                           LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                                           @PageableDefault(page = 1, size = 10, sort = {"id"}) Pageable pageable) throws CustomerNotFoundException {

        Response<List<CustomerDTO>> response = new Response<>();

        LocalDate startDateTime = CustomersApiUtil.convertLocalDateToLocalDateTime(startDate);
        LocalDate endDateTime = CustomersApiUtil.convertLocalDateToLocalDateTime(endDate);

        Page<Customer> customers = customerService.findBetweenDates(startDateTime, endDateTime, pageable);

        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("There are no customers registered between startDate=" + startDate
                    + " and endDate=" + endDate);
        }

        List<CustomerDTO> itemsDTO = new ArrayList<>();
        customers.stream().forEach(t -> itemsDTO.add(t.convertEntityToDTO()));



        response.setData(itemsDTO);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(CustomersApiUtil.HEADER_CUSTOMERS_API_VERSION, apiVersion);
        headers.add(CustomersApiUtil.HEADER_API_KEY, apiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }*/

    /**
     * Method that search for all the customers given an order number.
     *
     * @param apiVersion        - API version at the moment
     * @param apiKey            - API Key to access the routes
     * @param customerOrderNumber - the order number of the customer
     * @return ResponseEntity with a <code>Response<String></code> object and the HTTP status
     * <p>
     * HTTP Status:
     * <p>
     * 200 - OK: Everything worked as expected.
     * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
     * 404 - Not Found: The requested resource doesn't exist.
     * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
     * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential back-off of your requests.
     * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
     * @throws CustomerNotFoundException
     * @author Mariana Azevedo
     * @since 02/04/2020
     */
 /*   @GetMapping(value = "/byOrderNumber/{orderNumber}")
    @Operation(summary  = "Route to find a trip by the orderNumber in the API")
    public ResponseEntity<Response<List<CustomerDTO>>> findByOrderNumber(@RequestHeader(value = CustomersApiUtil.HEADER_CUSTOMERS_API_VERSION, defaultValue = "${api.version}")
                                                                         String apiVersion, @RequestHeader(value = CustomersApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey,
                                                                         @PathVariable("orderNumber") String customerOrderNumber) throws CustomerNotFoundException {

        Response<List<CustomerDTO>> response = new Response<>();
        List<Customer> customers = customerService.findAllByOrderNumber(customerOrderNumber);

        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("There are no customers registered with the orderNumber=" + customerOrderNumber);
        }

        List<CustomerDTO> customersDTO = new ArrayList<>();
        customers.stream().forEach(t -> customersDTO.add(t.convertEntityToDTO()));


        response.setData(customersDTO);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(CustomersApiUtil.HEADER_CUSTOMERS_API_VERSION, apiVersion);
        headers.add(CustomersApiUtil.HEADER_API_KEY, apiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }*/


}
