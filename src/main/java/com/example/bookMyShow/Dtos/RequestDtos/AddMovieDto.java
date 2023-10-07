package com.example.bookMyShow.Dtos.RequestDtos;

import com.example.bookMyShow.Enums.Genre;
import com.example.bookMyShow.Enums.Language;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;

@Data
public class AddMovieDto {

    private String movieName;

    private double duration;

    private double rating;

    private Date releaseDate;

    private Genre genre;

    private Language language;
}
