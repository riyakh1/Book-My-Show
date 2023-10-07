package com.example.bookMyShow.Dtos.RequestDtos;

import lombok.Data;

import java.util.List;

@Data
public class TicketRequestDto {
    private int showId;
    private int userId;
    private List<String> requestedSeats;
    //private boolean isMealAttached;

}
