package com.learn.ordersvc.client;

import com.learn.ordersvc.client.dto.TravelDTO;
import com.learn.ordersvc.common.response.Response;
import com.learn.ordersvc.common.util.OrdersApiUtil;
import feign.Headers;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "travel-service")
@Headers(value = {OrdersApiUtil.HEADER_TRAVELS_API_VERSION, OrdersApiUtil.HEADER_API_KEY})
@Validated
public interface TravelBookingClient {

    @PostMapping("/api-travel/v1/travel")
    @Valid
    @CircuitBreaker(name="travelClientCircuitBreaker")
    public ResponseEntity<Response<TravelDTO>> createTravel(@RequestBody TravelDTO dto);
}
