package com.learn.travelsvc.service.impl;


import com.learn.travelsvc.exception.TravelNotFoundException;
import com.learn.travelsvc.model.Travel;
import com.learn.travelsvc.repository.TravelRepository;
import com.learn.travelsvc.service.TravelService;
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
public class TravelServiceImpl implements TravelService {

    TravelRepository travelRepository;

    @Autowired
    public TravelServiceImpl(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    /**
     * @see TravelService#save(Travel)
     */
    @Override
    public Travel save(Travel travel) {
        return travelRepository.save(travel);
    }

    /**
     * @see TravelService#deleteById(Long)
     */
    @Override
    public void deleteById(Long travelId) {
        travelRepository.deleteById(travelId);
    }

    /**
     * @throws TravelNotFoundException
     * @see TravelService#findById(Long)
     */
    @Override
    public Travel findById(Long travelId) throws TravelNotFoundException {
        return travelRepository.findById(travelId).orElseThrow(() -> new TravelNotFoundException("Travel id=" + travelId + " not found"));
    }




}
