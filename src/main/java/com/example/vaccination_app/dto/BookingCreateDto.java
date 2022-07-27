package com.example.vaccination_app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BookingCreateDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date date;

    private String time;

    @NotNull
    private long vaccinationCenterId;

    @NotNull
    private long vaccineId;
}
