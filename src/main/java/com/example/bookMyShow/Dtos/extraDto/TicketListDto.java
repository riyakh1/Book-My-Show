package com.example.bookMyShow.Dtos.extraDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketListDto {
    private int totalTicketPrice;
    private String bookedSeats;
    private Date bookedAt;
}
