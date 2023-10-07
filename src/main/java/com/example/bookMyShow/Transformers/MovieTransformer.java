package com.example.bookMyShow.Transformers;

import com.example.bookMyShow.Dtos.RequestDtos.AddMovieDto;
import com.example.bookMyShow.Models.Movie;

public class MovieTransformer {

    public static Movie convertDtoToEntity(AddMovieDto addMovieDto){

        Movie movie = Movie.builder()
                .movieName(addMovieDto.getMovieName())
                .genre(addMovieDto.getGenre())
                .duration(addMovieDto.getDuration())
                .rating(addMovieDto.getRating())
                .language(addMovieDto.getLanguage())
                .releaseDate(addMovieDto.getReleaseDate())
                .build();

        return movie;
    }
}
