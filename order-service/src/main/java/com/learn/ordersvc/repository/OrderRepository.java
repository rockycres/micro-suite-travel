package com.learn.ordersvc.repository;


import com.learn.ordersvc.model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;



/**
 * Interface that implements the Travel Repository, with JPA CRUD methods
 * and other customized searches.
 *  
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderDetail, Long> {


	/**
	 * Method to search for all the travel in the same order number (unique number).
	 * 
	 * @author Mariana Azevedo
	 * @since 28/03/2020
	 * 
	 * @return <code>Optional<Travel></code> object
	 */
	List<OrderDetail> findAllByOrderId(Long orderId);
	
}
