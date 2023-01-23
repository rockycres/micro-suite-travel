package com.learn.ordersvc.model;


import com.learn.ordersvc.common.enumeration.OrderStatusEnum;
import com.learn.ordersvc.dto.model.OrderDetailDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderdetail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = -3656431259068389491L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "travel_id")
    private Long travelId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "order_received_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime orderReceivedDate;

    @Column(name = "order_actioned_date")
    private LocalDateTime orderActionedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatusEnum orderStatus = OrderStatusEnum.PENDING;

    public OrderDetail(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }


    /**
     * Method to convert an orderdetail entity to an orderdetail DTO.
     *
     * @return a <code>TravelDTO</code> object
     */
    public OrderDetailDTO convertEntityToDTO() {
        return new ModelMapper().map(this, OrderDetailDTO.class);
    }

}
