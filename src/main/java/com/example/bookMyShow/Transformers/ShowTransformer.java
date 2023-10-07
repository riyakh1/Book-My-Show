package com.example.bookMyShow.Transformers;

import com.example.bookMyShow.Dtos.RequestDtos.AddShowDto;
import com.example.bookMyShow.Dtos.RequestDtos.ShowTimeDto;
import com.example.bookMyShow.Dtos.ResponseDtos.ShowTimeResponseDto;
import com.example.bookMyShow.Models.Show;

public class ShowTransformer {
    public static Show convertDtoToEntity(AddShowDto addShowDto){

        //we didnt take the movie and theater id because we can simply get these entities from their repository
        Show show = Show.builder()
                .time(addShowDto.getShowStartTime())
                .date(addShowDto.getShowDate())
                .build();

        return show;
    }

    public static ShowTimeResponseDto convertShowTimeDtoToResponseDto(Show show){
        ShowTimeResponseDto showTimeResponseDto = ShowTimeResponseDto.builder()
                .showTime(show.getTime())
                .build();

        return showTimeResponseDto;
    }
}
