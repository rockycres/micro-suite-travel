package com.learn.customersvc.model;


import com.learn.customersvc.common.enumeration.CustomerProfileEnum;
import com.learn.customersvc.common.enumeration.GenderTypeEnum;
import com.learn.customersvc.dto.model.CustomerDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = -3656431259068389491L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "givenname")
    private String givenName;

    @Column(name = "surname")
    private String surName;

    @Column(name = "dob")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderTypeEnum gender;
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_profile")
    private CustomerProfileEnum customerProfile = CustomerProfileEnum.BASIC;

    public Customer(CustomerProfileEnum customerProfile) {
        this.customerProfile = customerProfile;
    }

    public Customer(GenderTypeEnum gender) {
        this.gender = gender;
    }

    /**
     * Method to convert a Customer entity to a Customer DTO.
     *
     * @return a <code>CustomerDTO</code> object
     */
    public CustomerDTO convertEntityToDTO() {
        return new ModelMapper().map(this, CustomerDTO.class);
    }

}
