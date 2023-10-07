package com.example.bookMyShow.Transformers;

import com.example.bookMyShow.Dtos.RequestDtos.TheaterEntryDto;
import com.example.bookMyShow.Models.Theater;

public class TheaterTransformer {

    public static Theater convertDtoToEntity(TheaterEntryDto theaterEntryDto){
        Theater theater = Theater.builder()
                .name(theaterEntryDto.getName())
                .location(theaterEntryDto.getLocation())
                .build();

        return theater;
    }
}
