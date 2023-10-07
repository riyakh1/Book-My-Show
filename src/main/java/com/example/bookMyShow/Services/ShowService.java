package com.example.bookMyShow.Services;

import com.example.bookMyShow.Dtos.RequestDtos.AddShowDto;
import com.example.bookMyShow.Dtos.RequestDtos.ShowSeatsDto;
import com.example.bookMyShow.Dtos.RequestDtos.ShowTimeDto;
import com.example.bookMyShow.Dtos.ResponseDtos.ShowTimeResponseDto;
import com.example.bookMyShow.Enums.SeatType;
import com.example.bookMyShow.Exceptions.MovieNotFoundException;
import com.example.bookMyShow.Exceptions.ShowNotFoundException;
import com.example.bookMyShow.Exceptions.TheaterNotFoundException;
import com.example.bookMyShow.Models.*;
import com.example.bookMyShow.Repositories.MovieRepository;
import com.example.bookMyShow.Repositories.ShowRepository;
import com.example.bookMyShow.Repositories.TheaterRepository;
import com.example.bookMyShow.Transformers.ShowTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    @Autowired
    ShowRepository showRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheaterRepository theaterRepository;


    public String addShow(AddShowDto addShowDto) throws MovieNotFoundException , TheaterNotFoundException{

        //Calling the transformer
        Show show = ShowTransformer.convertDtoToEntity(addShowDto);

        //Get the movie entity from the movie repostiory
        Optional<Movie> movieOptional = movieRepository.findById(addShowDto.getMovieId());

        //Validation
        if(movieOptional.isEmpty()){
            throw new MovieNotFoundException("Movie Not Found");
        }

        //Get the theater entity from the theater repository
        Optional<Theater> theaterOptional = theaterRepository.findById(addShowDto.getTheaterId());

        //Validation
        if(theaterOptional.isEmpty()){
            throw new TheaterNotFoundException("Theater Not Found");
        }

        Movie movie = movieOptional.get();
        Theater theater = theaterOptional.get();

        //Setting the parent in child table
        //Saving the PK
        show = showRepository.save(show);

        //Uni directional mapping
        show.setMovie(movie);
        show.setTheater(theater);

        //Saving the child in parent table
        //Bi directional mapping
        movie.getShowList().add(show);
        theater.getShowList().add(show);

        //Saving the parent
        movieRepository.save(movie);
        theaterRepository.save(theater);


        return "Show Added Successfully";
    }

    public String associateShowSeats(ShowSeatsDto showSeatsDto) throws ShowNotFoundException{
        Optional<Show> showOptional = showRepository.findById(showSeatsDto.getShowId());

        //Validation
        if(showOptional.isEmpty()){
            throw new ShowNotFoundException("Show Id is Incorrect!!");
        }

        //Valid show
        Show show = showOptional.get();

        //We need theater seats
        Theater theater = show.getTheater();
        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();

        List<ShowSeat> showSeatList = show.getShowSeatList();

        //For each theater seat I want to create a show seat
        //Iterating over the theater seat list
        for(TheaterSeat theaterSeat : theaterSeatList){
            ShowSeat showSeat = new ShowSeat();

            //Setting the attributes
            showSeat.setSeatNo(theaterSeat.getSeatNo());
            showSeat.setSeatType(theaterSeat.getSeatType());

            //Setting price for premium and classic seats
            if(showSeat.getSeatType().equals(SeatType.BASIC))
                showSeat.setPrice(showSeatsDto.getPriceForClassicSeats());
            else
                showSeat.setPrice(showSeatsDto.getPriceForPremiumSeats());

            showSeat.setAvailable(true); //initially its available
            showSeat.setFoodAttached(false);

            //Setting the foreign key --> unidirectional mapping
            showSeat.setShow(show);

            //bi-directional mapping
            showSeatList.add(showSeat);
        }

        //Saving the parent only
        showRepository.save(show);
        return "Show Seat has been successfully added";
    }

    public ShowTimeResponseDto showTimeOfAMovieInATheater(ShowTimeDto showTimeDto)throws MovieNotFoundException , TheaterNotFoundException , ShowNotFoundException{
            Optional<Theater> theaterOptional = theaterRepository.findById(showTimeDto.getTheaterId());
            if(theaterOptional.isEmpty()){
                throw new TheaterNotFoundException("Theater Id is Incorrect!!");
            }
            Optional<Movie> movieOptional = movieRepository.findById(showTimeDto.getMovieId());
            if(movieOptional.isEmpty()){
                throw new MovieNotFoundException("Movie Id is Incorrect!!");
            }
            Theater theater = theaterOptional.get();
            Movie movie = movieOptional.get();
            List<Show> showList = theater.getShowList();
            for(Show show : showList) {
                if (show.getMovie().getId() == movie.getId()) {
                    return ShowTransformer.convertShowTimeDtoToResponseDto(show);
                }
            }
            throw new ShowNotFoundException("Show is currently not Available");

        //        Optional<Movie> movieOptional = movieRepository.findById(showTimeDto.getMovieId());
//        //Valdation check for movie
//        if(movieOptional.isEmpty()){
//            throw new MovieNotFoundException("Movie Id is incorrect!!");
//        }
//
//        Optional<Theater> theaterOptional = theaterRepository.findById(showTimeDto.getTheaterId());
//        //Validation check for theater
//        if(theaterOptional.isEmpty()){
//            throw new TheaterNotFoundException("Theater Id is incorrect!!");
//        }
//
//        Movie movie = movieOptional.get();
//        Theater theater = theaterOptional.get();
//
//        //Get the list of shows from this theater
//        List<Show> showList = theater.getShowList();
//
//        //Check whether this list of shows has the movie we're looking for
//        if(showList.contains(movie)){
//            for(Show show : showList){
//                if(show.getMovie().getId() == movie.getId()) return ShowTransformer.convertShowTimeDtoToResponseDto(show);
//            }
//        }
//
//        throw new ShowNotFoundException("Show is not available");
//    }
        }
}
