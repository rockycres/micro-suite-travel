package com.learn.travelsvc.repository;



import com.learn.travelsvc.model.Travel;
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
public interface TravelRepository extends JpaRepository<Travel, Long> {

}
