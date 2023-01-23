package com.learn.travelsvc.controller;


import com.learn.travelsvc.common.response.Response;
import com.learn.travelsvc.common.util.TravelsApiUtil;
import com.learn.travelsvc.dto.model.TravelDTO;
import com.learn.travelsvc.exception.NotParsableContentException;
import com.learn.travelsvc.exception.TravelInvalidUpdateException;
import com.learn.travelsvc.exception.TravelNotFoundException;
import com.learn.travelsvc.model.Travel;
import com.learn.travelsvc.service.TravelService;

import io.swagger.v3.oas.annotations.Operation;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@RestController
@RequestMapping("/api-travel/v1/travel")
public class TravelController {

    TravelService travelService;

    @Autowired
    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }


    @PostMapping
    @Operation(summary = "Route to create travel")
    public ResponseEntity<Response<TravelDTO>> createTravel(@RequestHeader(value = TravelsApiUtil.HEADER_TRAVELS_API_VERSION, defaultValue = "${api.version}") String apiVersion,
                                                      @RequestHeader(value = TravelsApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey, @Valid @RequestBody TravelDTO dto, BindingResult result)
            throws NotParsableContentException {

        Response<TravelDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        if (!TravelsApiUtil.isTravelDTOEndDateGreaterThanStartDate(dto)) {
            throw new NotParsableContentException("The travel's start date is greater than travel's end date.");
        }

        Travel travel = dto.convertDTOToEntity();
        Travel travelToCreate = travelService.save(travel);

        TravelDTO dtoSaved = travelToCreate.convertEntityToDTO();


        response.setData(dtoSaved);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
        headers.add(TravelsApiUtil.HEADER_API_KEY, apiKey);
        log.info("Create Travel Service Executed successfully",dtoSaved.getTravelId());
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }


    @PutMapping(path = "/{id}")
    @Operation(summary = "Route to update a travel")
    public ResponseEntity<Response<TravelDTO>> update(@RequestHeader(value = TravelsApiUtil.HEADER_TRAVELS_API_VERSION, defaultValue = "${api.version}") String apiVersion,
                                                      @RequestHeader(value = TravelsApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey, @Valid @RequestBody TravelDTO dto, BindingResult result)
            throws TravelNotFoundException, TravelInvalidUpdateException, NotParsableContentException {

        Response<TravelDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        if (!TravelsApiUtil.isTravelDTOEndDateGreaterThanStartDate(dto)) {
            throw new NotParsableContentException("The travel's start date is greater than travel's end date.");
        }

        Travel travelToFind = travelService.findById(dto.getTravelId());
        if (travelToFind.getTravelId().compareTo(dto.getTravelId()) != 0) {
            throw new TravelInvalidUpdateException("You don't have permission to change the travel id=" + dto.getTravelId());
        }

        Travel travel = dto.convertDTOToEntity();
        Travel travelToUpdate = travelService.save(travel);

        TravelDTO itemDTO = travelToUpdate.convertEntityToDTO();
        response.setData(itemDTO);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
        headers.add(TravelsApiUtil.HEADER_API_KEY, apiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    @Operation(summary = "Route to find a trip by your id in the API")
    public ResponseEntity<Response<TravelDTO>> findById(
            @RequestHeader(value = TravelsApiUtil.HEADER_TRAVELS_API_VERSION, defaultValue = "${api.version}") String apiVersion,
            @RequestHeader(value = TravelsApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey,
            @PathVariable("id") Long travelId)
            throws TravelNotFoundException {

        Response<TravelDTO> response = new Response<>();
        Travel travel = travelService.findById(travelId);

        TravelDTO dto = travel.convertEntityToDTO();


        response.setData(dto);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
        headers.add(TravelsApiUtil.HEADER_API_KEY, apiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Route to delete a trip in the API")
    public ResponseEntity<Response<String>> delete(@RequestHeader(value = TravelsApiUtil.HEADER_TRAVELS_API_VERSION, defaultValue = "${api.version}")
                                                   String apiVersion, @RequestHeader(value = TravelsApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey,
                                                   @PathVariable("id") Long travelId) throws TravelNotFoundException {

        Response<String> response = new Response<>();
        Travel travel = travelService.findById(travelId);

        travelService.deleteById(travel.getTravelId());
        response.setData("Travel id=" + travel.getTravelId() + " successfully deleted");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(TravelsApiUtil.HEADER_TRAVELS_API_VERSION, apiVersion);
        headers.add(TravelsApiUtil.HEADER_API_KEY, apiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.NO_CONTENT);
    }



}
