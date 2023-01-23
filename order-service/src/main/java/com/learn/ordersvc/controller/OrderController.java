package com.learn.ordersvc.controller;


import com.learn.ordersvc.client.TravelBookingClient;
import com.learn.ordersvc.client.dto.TravelDTO;
import com.learn.ordersvc.common.enumeration.OrderStatusEnum;
import com.learn.ordersvc.common.response.Response;
import com.learn.ordersvc.common.util.OrdersApiUtil;
import com.learn.ordersvc.dto.model.OrderDetailDTO;
import com.learn.ordersvc.dto.model.SubmitOrderRequestDTO;
import com.learn.ordersvc.exception.FeignClientException;
import com.learn.ordersvc.exception.NotParsableContentException;
import com.learn.ordersvc.exception.OrderInvalidUpdateException;
import com.learn.ordersvc.exception.OrderNotFoundException;
import com.learn.ordersvc.model.OrderDetail;
import com.learn.ordersvc.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;


@Log4j2
@RestController
@RequestMapping("/api-order/v1/order")
public class OrderController {

    OrderService orderService;

    TravelBookingClient travelBookingClient;

    @Autowired
    public OrderController(OrderService orderService, TravelBookingClient travelBookingClient) {
        this.orderService = orderService;
        this.travelBookingClient = travelBookingClient;
    }


    @PostMapping
    @Operation(summary = "Route to create travel order")
    @Transactional
    public ResponseEntity<Response<OrderDetailDTO>> createTravelOrder(@RequestHeader(value = OrdersApiUtil.HEADER_ORDERS_API_VERSION, defaultValue = "${api.version}") String apiVersion, @RequestHeader(value = OrdersApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey, @Valid @RequestBody SubmitOrderRequestDTO travelOrderRequest, BindingResult result) throws NotParsableContentException, FeignClientException {

        Response<OrderDetailDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        OrderDetail orderDetailRequest = new OrderDetail();
        orderDetailRequest.setCustomerId(travelOrderRequest.getCustomerId());
        OrderDetail orderDetailCreated = orderService.save(orderDetailRequest);


        TravelDTO travelRequest = travelOrderRequest.convertInputRequestDTOToClientRequestDTO();
        try {

            ResponseEntity<Response<TravelDTO>> travelResponse = travelBookingClient.createTravel(travelRequest);
            long travelId = travelResponse.getBody().getData().getTravelId();
            orderDetailCreated.setTravelId(travelId);
            orderDetailCreated.setOrderStatus(OrderStatusEnum.COMPLETED);
            orderDetailCreated.setOrderActionedDate(LocalDateTime.now());
            orderService.save(orderDetailCreated);

        } catch (Exception e) {
            orderDetailCreated.setOrderStatus(OrderStatusEnum.FAILED);
            orderDetailCreated.setOrderActionedDate(LocalDateTime.now());
            orderService.save(orderDetailCreated);
            throw new FeignClientException("communication issue with create travel service :"+ e.getLocalizedMessage() , e);
        }


        OrderDetailDTO dtoSaved = orderDetailCreated.convertEntityToDTO();

        response.setData(dtoSaved);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(OrdersApiUtil.HEADER_ORDERS_API_VERSION, apiVersion);
        headers.add(OrdersApiUtil.HEADER_API_KEY, apiKey);
        log.info("Create Order Service Executed successfully",dtoSaved.getOrderId());

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }


    @PutMapping(path = "/{orderId}")
    @Operation(summary = "Route to update a order")
    public ResponseEntity<Response<OrderDetailDTO>> update(@RequestHeader(value = OrdersApiUtil.HEADER_ORDERS_API_VERSION, defaultValue = "${api.version}") String apiVersion, @RequestHeader(value = OrdersApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey, @Valid @RequestBody OrderDetailDTO dto, BindingResult result) throws OrderNotFoundException, OrderInvalidUpdateException, NotParsableContentException {

        Response<OrderDetailDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }


        OrderDetail orderDetailToFind = orderService.findById(dto.getOrderId());
        if (orderDetailToFind.getOrderId().compareTo(dto.getOrderId()) != 0) {
            throw new OrderInvalidUpdateException("You don't have permission to change the travel id=" + dto.getOrderId());
        }

        OrderDetail orderDetail = dto.convertDTOToEntity();
        OrderDetail orderDetailToUpdate = orderService.save(orderDetail);

        OrderDetailDTO itemDTO = orderDetailToUpdate.convertEntityToDTO();
        response.setData(itemDTO);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(OrdersApiUtil.HEADER_ORDERS_API_VERSION, apiVersion);
        headers.add(OrdersApiUtil.HEADER_API_KEY, apiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }


    @GetMapping(value = "/{orderId}")
    @Operation(summary = "Route to find a trip by your id in the API")
    public ResponseEntity<Response<OrderDetailDTO>> findById(@RequestHeader(value = OrdersApiUtil.HEADER_ORDERS_API_VERSION, defaultValue = "${api.version}") String apiVersion, @RequestHeader(value = OrdersApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey, @PathVariable("orderId") Long orderId) throws OrderNotFoundException {

        Response<OrderDetailDTO> response = new Response<>();
        OrderDetail orderDetail = orderService.findById(orderId);

        OrderDetailDTO dto = orderDetail.convertEntityToDTO();


        response.setData(dto);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(OrdersApiUtil.HEADER_ORDERS_API_VERSION, apiVersion);
        headers.add(OrdersApiUtil.HEADER_API_KEY, apiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }


    @DeleteMapping(value = "/{orderId}")
    @Operation(summary = "Route to delete a trip in the API")
    public ResponseEntity<Response<String>> delete(@RequestHeader(value = OrdersApiUtil.HEADER_ORDERS_API_VERSION, defaultValue = "${api.version}") String apiVersion, @RequestHeader(value = OrdersApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey, @PathVariable("orderId") Long orderId) throws OrderNotFoundException {

        Response<String> response = new Response<>();
        OrderDetail orderDetail = orderService.findById(orderId);

        orderService.deleteById(orderDetail.getOrderId());
        response.setData("Travel id=" + orderDetail.getOrderId() + " successfully deleted");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(OrdersApiUtil.HEADER_ORDERS_API_VERSION, apiVersion);
        headers.add(OrdersApiUtil.HEADER_API_KEY, apiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.NO_CONTENT);
    }


   /* @GetMapping
    @Operation(summary  = "Route to find all orders of the API in a period of time")
    public ResponseEntity<Response<List<OrderDetailDTO>>> findAllBetweenDates(@RequestHeader(value = OrdersApiUtil.HEADER_ORDERS_API_VERSION, defaultValue = "${api.version}")
                                                                              String apiVersion, @RequestHeader(value = OrdersApiUtil.HEADER_API_KEY, defaultValue = "${api.key}") String apiKey, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                              LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                                              @PageableDefault(page = 1, size = 10, sort = {"id"}) Pageable pageable) throws OrderNotFoundException {

        Response<List<OrderDetailDTO>> response = new Response<>();

        LocalDateTime startDateTime = OrdersApiUtil.convertLocalDateToLocalDateTime(startDate);
        LocalDateTime endDateTime = OrdersApiUtil.convertLocalDateToLocalDateTime(endDate);

        Page<OrderDetail> orders = orderService.findBetweenDates(startDateTime, endDateTime, pageable);

        if (orders.isEmpty()) {
            throw new OrderNotFoundException("There are no orders registered between startDate=" + startDate
                    + " and endDate=" + endDate);
        }

        List<OrderDetailDTO> itemsDTO = new ArrayList<>();
        orders.stream().forEach(t -> itemsDTO.add(t.convertEntityToDTO()));



        response.setData(itemsDTO);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(OrdersApiUtil.HEADER_ORDERS_API_VERSION, apiVersion);
        headers.add(OrdersApiUtil.HEADER_API_KEY, apiKey);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
*/


}
