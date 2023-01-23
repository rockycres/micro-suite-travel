package com.learn.fallbackservice.controller;


import com.learn.fallbackservice.common.response.Response;
import com.learn.fallbackservice.dto.model.FallbackResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequestMapping("/api-fallback/v1")
public class FallbackController {

    @GetMapping
    public ResponseEntity<Response> fallbackCustomerAPI(){

        Response response = new Response();

        FallbackResponseDTO dto = dummyResponse();

        response.setData(dto);

        return new ResponseEntity<>(response, HttpStatus.TEMPORARY_REDIRECT);
    }

    private FallbackResponseDTO dummyResponse() {
        return  new FallbackResponseDTO("Fallback response from dummy endpoint");
    }


}
