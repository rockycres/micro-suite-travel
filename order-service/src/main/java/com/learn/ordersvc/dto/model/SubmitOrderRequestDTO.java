package com.learn.ordersvc.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.learn.ordersvc.client.dto.TravelDTO;
import com.learn.ordersvc.model.OrderDetail;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitOrderRequestDTO {



    @NotNull(message = "CustomerId cannot be null")
    private Long customerId;

    private Long travelId;

    @NotNull(message = "Source cannot be null")
    private String source;

    @NotNull(message = "destination cannot be null")
    private String destination;

    @NotNull(message = "TravelType cannot be null")
    @Pattern(regexp = "^(ONE-WAY|RETURN|MULTI-CITY)$", message = "For the TravelType, only the values ONE-WAY, RETURN or MULTI-CITY are accepted.")
    private String travelType;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "en-US", timezone = "Brazil/East")
    private LocalDate startDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "en-US", timezone = "Brazil/East")
    private LocalDate endDate;


    /**
     * Method to convert a Travel DTO to a Travel entity.
     *
     *
     * @return a <code>Travel</code> object
     */
    public TravelDTO convertInputRequestDTOToClientRequestDTO() {
        return new ModelMapper().map(this, TravelDTO.class);
    }


}
