package com.example.bookMyShow.Dtos.RequestDtos;

import lombok.Data;

@Data
public class TheaterSeatsEntryDto {
    private int noOfSeatsIn1Row;

    private int noOfClassicSeats;

    private int noOfPremiumSeats;

    //this is most important because we need to know in which theater we're adding the seats
    private String location;
}
