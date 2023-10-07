package com.example.bookMyShow.Services;

import com.example.bookMyShow.Dtos.RequestDtos.AddMovieDto;
import com.example.bookMyShow.Dtos.RequestDtos.TotalMovieRevenueDto;
import com.example.bookMyShow.Exceptions.MovieNotFoundException;
import com.example.bookMyShow.Models.Movie;
import com.example.bookMyShow.Models.Show;
import com.example.bookMyShow.Models.Ticket;
import com.example.bookMyShow.Repositories.MovieRepository;
import com.example.bookMyShow.Transformers.MovieTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    public String addMovie(AddMovieDto addMovieDto) {

        Movie movie = MovieTransformer.convertDtoToEntity(addMovieDto);
        movieRepository.save(movie);

        return "Movie Added Successfully";
    }

    public String movieWithTheMaximumNumberOfShows() {
        List<Movie> movieList = movieRepository.findAll();

        int max = 0;
        Movie movieObj = null;

        for(Movie movie : movieList){
            if(movie.getShowList().size() > max){
                max = movie.getShowList().size();
                movieObj = movie;
            }
        }
        return movieObj.getMovieName();
    }

    public int totalRevenueOfAParticularMovie(int movieId) throws MovieNotFoundException{

        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        //Validation check
        if(movieOptional.isEmpty()){
            throw new MovieNotFoundException("Movie Id is Incorrect!!");
        }

        Movie movie = movieOptional.get();
        int totalRevenue = 0;

        List<Show> showList = movie.getShowList();
        for(Show show : showList){
            List<Ticket> ticketList = show.getTicketList();
            for(Ticket ticket : ticketList){
                totalRevenue += ticket.getTotalTicketPrice();
            }
        }

        return totalRevenue;
    }

    public String movieHitOrFlop(TotalMovieRevenueDto movieRevenueDto)throws MovieNotFoundException{
        int movieId = movieRevenueDto.getMovieId();
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        //Validation Check
        if(movieOptional.isEmpty()){
            throw new MovieNotFoundException("This Movie Id id Incorrect!!");
        }

        Movie movie = movieOptional.get();

        int totalRevenue = totalRevenueOfAParticularMovie(movie.getId());

        if(totalRevenue < movieRevenueDto.getBudget()) return "Flop";
        else if(totalRevenue == movieRevenueDto.getBudget()) return "Average";
        else return "Hit";
    }
}
