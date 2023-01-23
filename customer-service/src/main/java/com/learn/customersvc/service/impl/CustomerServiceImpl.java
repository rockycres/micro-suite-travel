package com.learn.customersvc.service.impl;


import com.learn.customersvc.exception.CustomerNotFoundException;
import com.learn.customersvc.model.Customer;
import com.learn.customersvc.repository.CustomerRepository;
import com.learn.customersvc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class that implements the travel's service methods
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository travelRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    /**
     * @see CustomerService#save(Customer)
     */
    @Override
    public Customer save(Customer travel) {
        return travelRepository.save(travel);
    }

    /**
     * @see CustomerService#findAllByOrderNumber(String)
     */
    /*@Override
    public List<Customer> findAllByOrderNumber(String orderNumber) {
        return travelRepository.findAllByOrderNumber(orderNumber);
    }*/

    /**
     * @see CustomerService#deleteById(Long)
     */
    @Override
    public void deleteById(Long travelId) {
        travelRepository.deleteById(travelId);
    }

    /**
     * @throws CustomerNotFoundException
     * @see CustomerService#findById(Long)
     */
    @Override
    public Customer findById(Long id) throws CustomerNotFoundException {
        return travelRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer id=" + id + " not found"));
    }

    /**
     * @see CustomerService#findBetweenDates(LocalDateTime, LocalDateTime, Pageable)
     */
  /*  @Override
    public Page<Customer> findBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return travelRepository.findAllByStartDateGreaterThanEqualAndStartDateLessThanEqual(startDate, endDate, pageable);
    }*/

    /**
     * @see CustomerService#findAll()
     */
    @Override
    public List<Customer> findAll() {
        return travelRepository.findAll();
    }


}
