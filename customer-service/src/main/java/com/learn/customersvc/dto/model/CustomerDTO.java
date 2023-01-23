package com.learn.customersvc.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.learn.customersvc.model.Customer;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {

    private Long customerId;

    @NotNull(message = "GivenName cannot be null")
    private String givenName;

    @NotNull(message = "SurName cannot be null")
    private String surName;

    @NotNull(message = "DOB cannot be null")
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "en-US", timezone = "Brazil/East")
    private LocalDate dob;

    @NotNull(message = "Gender cannot be null")
    @Pattern(regexp = "^(MALE|FEMALE|TRANSGENDER)$", message = "For the gender, only the values MALE,FEMALE or TRANSGENDER are accepted.")
    private String gender;


    private String customerProfile;


    /**
     * Method to convert a customer DTO to a customer entity.
     *
     * @return a <code>Customer</code> object
     */
    public Customer convertDTOToEntity() {
        return new ModelMapper().map(this, Customer.class);
    }

}
