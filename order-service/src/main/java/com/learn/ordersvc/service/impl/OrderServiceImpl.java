package com.learn.ordersvc.service.impl;


import com.learn.ordersvc.exception.OrderNotFoundException;
import com.learn.ordersvc.model.OrderDetail;
import com.learn.ordersvc.repository.OrderRepository;
import com.learn.ordersvc.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class that implements the travel's service methods
 */
@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * @see OrderService#save(OrderDetail)
     */
    @Override
    public OrderDetail save(OrderDetail travelOrderDetail) {
        return orderRepository.save(travelOrderDetail);
    }

    /**
     * @see OrderService#findAllByOrderId(Long)
     */
    @Override
    public List<OrderDetail> findAllByOrderId(Long orderId) {
        return orderRepository.findAllByOrderId(orderId);
    }

    /**
     * @see OrderService#deleteById(Long)
     */
    @Override
    public void deleteById(Long travelId) {
        orderRepository.deleteById(travelId);
    }

    /**
     * @throws OrderNotFoundException
     * @see OrderService#findById(Long)
     */
    @Override
    public OrderDetail findById(Long travelId) throws OrderNotFoundException {
        return orderRepository.findById(travelId).orElseThrow(() -> new OrderNotFoundException("Travel id=" + travelId + " not found"));
    }


    /**
     * @see OrderService#findAll()
     */
    @Override
    public List<OrderDetail> findAll() {
        return orderRepository.findAll();
    }


}
