package com.learn.travelsvc.service;

import com.learn.travelsvc.exception.TravelNotFoundException;
import com.learn.travelsvc.model.Travel;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Service Interface that provides methods for manipulating Travel objects.
 *
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public interface TravelService {

    /**
     * Method that save an object Travel.
     *
     * @param travel
     * @return <code>Travel</code> object
     * @author Mariana Azevedo
     * @since 08/09/2019
     */
    Travel save(Travel travel);

    /**
     * Method that remove an object Travel by an id.
     *
     * @param travelId
     * @author Mariana Azevedo
     * @since 08/09/2019
     */
    void deleteById(Long travelId);

    /**
     * Method that find an object Travel by an id.
     *
     * @param travelId
     * @return <code>Optional<Travel></code> object
     * @author Mariana Azevedo
     * @since 08/09/2019
     */
    Travel findById(Long travelId) throws TravelNotFoundException;



}
