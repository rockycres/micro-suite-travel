package com.learn.travelsvc.model;


import com.learn.travelsvc.common.enumeration.TravelTypeEnum;
import com.learn.travelsvc.dto.model.TravelDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "traveldetail")
public class Travel implements Serializable {

    private static final long serialVersionUID = -3656431259068389491L;



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Long travelId;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "travel_type")
    private TravelTypeEnum travelType;

    public Travel(TravelTypeEnum travelType) {
        this.travelType = travelType;
    }

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

    /**
     * Method to convert an Travel entity to a Travel DTO.
     *
     * @return a <code>TravelDTO</code> object
     */
    public TravelDTO convertEntityToDTO() {
        return new ModelMapper().map(this, TravelDTO.class);
    }

}
