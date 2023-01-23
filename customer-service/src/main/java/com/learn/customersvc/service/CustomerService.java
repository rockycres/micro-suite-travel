package com.learn.customersvc.service;





import com.learn.customersvc.exception.CustomerNotFoundException;
import com.learn.customersvc.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Service Interface that provides methods for manipulating Customer objects.
 *
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public interface CustomerService {

    /**
     * Method that save an object Customer.
     *
     * @param customer
     * @return <code>Customer</code> object
     * @author Mariana Azevedo
     * @since 08/09/2019
     */
    Customer save(Customer customer);

    /**
     * Method that remove an object Customer by an id.
     *
     * @param customerId
     * @author Mariana Azevedo
     * @since 08/09/2019
     */
    void deleteById(Long customerId);

    /**
     * Method that find an object Customer by an id.
     *
     * @param id
     * @return <code>Optional<Customer></code> object
     * @author Mariana Azevedo
     * @since 08/09/2019
     */
    Customer findById(Long id) throws CustomerNotFoundException;

    /**
     * Method that find one or more trips by orderNumber.
     *
     * @param orderNumber
     * @return <code>Optional<Customer></code> object
     * @author Mariana Azevedo
     * @since 08/09/2019
     */
   // List<Customer> findAllByOrderNumber(String orderNumber);

    /**
     * Method that find all customers.
     *
     * @return <code>List<Customers></code> object
     * @author Mariana Azevedo
     * @since 08/09/2019
     */
    List<Customer> findAll();

    /**
     * Method that find all customers in a period of time.
     *
     * @param startDate - the start date of the search
     * @param endDate   - the end date of the search
     * @param pageable  - object for pagination information: the page that will be return in the search,
     *                  the size of page, and sort direction that the results should be shown: ASC - ascending order;
     *                  DESC - descending order.
     * @return <code>Page<Customer></code> object
     * @author Mariana Azevedo
     * @since 21/08/2020
     */
    //Page<Customer> findBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);


}
