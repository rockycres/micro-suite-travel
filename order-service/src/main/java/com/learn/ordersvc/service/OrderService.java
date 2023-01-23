package com.learn.ordersvc.service;



import com.learn.ordersvc.exception.OrderNotFoundException;
import com.learn.ordersvc.model.OrderDetail;

import java.util.List;


/**
 * Service Interface that provides methods for manipulating Travel objects.
 *
 * @author Mariana Azevedo
 * @since 08/09/2019
 */
public interface OrderService {

    /**
     * Method that save an object Travel.
     *
     * @param travelOrderDetail
     * @return <code>Travel</code> object
     * @author Mariana Azevedo
     * @since 08/09/2019
     */
    OrderDetail save(OrderDetail travelOrderDetail);

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
    OrderDetail findById(Long travelId) throws OrderNotFoundException;

    /**
     * Method that find one or more trips by orderNumber.
     *
     * @param orderId
     * @return <code>Optional<Travel></code> object
     * @author Mariana Azevedo
     * @since 08/09/2019
     */
    List<OrderDetail> findAllByOrderId(Long orderId);

    /**
     * Method that find all travels.
     *
     * @return <code>List<Travels></code> object
     * @author Mariana Azevedo
     * @since 08/09/2019
     */
    List<OrderDetail> findAll();



}
