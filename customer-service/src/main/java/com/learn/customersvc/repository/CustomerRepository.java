package com.learn.customersvc.repository;



import com.learn.customersvc.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;



/**
 * Interface that implements the Customer Repository, with JPA CRUD methods
 * and other customized searches.
 *  
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	/**
	 * Method to search for all the travels in the API in a period of time.
	 * 
	 * @author Mariana Azevedo
	 * @since 21/08/2020
	 * 
	 * @return <code>Page<Customer></code> object
	 */
/*	Page<Customer> findAllByStartDateGreaterThanEqualAndStartDateLessThanEqual
		(LocalDateTime startDate, LocalDateTime endDate, Pageable pg);
	*/
	/**
	 * Method to search for all the travel in the same order number (unique number).
	 * 
	 * @author Mariana Azevedo
	 * @since 28/03/2020
	 * 
	 * @return <code>Optional<Customer></code> object
	 */
	//List<Customer> findAllByOrderNumber(String orderNumber);
	
}
