package com.learn.travelsvc.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.learn.travelsvc.common.validator.ConditionalValidation;
import com.learn.travelsvc.model.Travel;
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
@ConditionalValidation(conditionalProperty = "endDate", values = {"RETURN"}, requiredProperties = {"travelType"},
        message = "Enddate is required for RETURN trip.")
public class TravelDTO  {


    private Long travelId;

    @NotNull(message = "Start Date cannot be null")
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "en-US", timezone = "Brazil/East")
    private LocalDate startDate;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "en-US", timezone = "Brazil/East")
    private LocalDate endDate;

    @NotNull(message = "TravelType cannot be null")
    @Pattern(regexp = "^(ONE-WAY|RETURN)$", message = "For the TravelType, only the values ONE-WAY or RETURN are accepted.")
    private String travelType;

    @NotNull(message = "source cannot be null")
    private String source;


    @NotNull(message = "destination cannot be null")
    private String destination;


    /**
     * Method to convert a Travel DTO to a Travel entity.
     *
     *
     * @return a <code>Travel</code> object
     */
    public Travel convertDTOToEntity() {
        return new ModelMapper().map(this, Travel.class);
    }

}
