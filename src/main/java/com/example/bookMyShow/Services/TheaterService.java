package com.example.bookMyShow.Services;

import com.example.bookMyShow.Dtos.RequestDtos.TheaterEntryDto;
import com.example.bookMyShow.Dtos.RequestDtos.TheaterSeatsEntryDto;
import com.example.bookMyShow.Enums.SeatType;
import com.example.bookMyShow.Models.Show;
import com.example.bookMyShow.Models.Theater;
import com.example.bookMyShow.Models.TheaterSeat;
import com.example.bookMyShow.Repositories.TheaterRepository;
import com.example.bookMyShow.Transformers.TheaterTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TheaterService {
    @Autowired
    TheaterRepository theaterRepository;

    public String addTheater(TheaterEntryDto theaterEntryDto) {
        //Convert dto to entity and then save it
        //Calling the transformer
        Theater theater = TheaterTransformer.convertDtoToEntity(theaterEntryDto);
        theaterRepository.save(theater);
        return "Theater has been saved successfully";
    }

    public String addTheaterSeats(TheaterSeatsEntryDto theaterSeatsEntryDto) {
        int columns = theaterSeatsEntryDto.getNoOfSeatsIn1Row();
        int noOfClassicSeats = theaterSeatsEntryDto.getNoOfClassicSeats();
        int noOfPremiumSeats = theaterSeatsEntryDto.getNoOfPremiumSeats();
        String location = theaterSeatsEntryDto.getLocation();

        //get the theater entity with this location
        Theater theater = theaterRepository.findByLocation(location);

        int counter = 1;
        char ch = 'A';

        //Setting the attributes for classic seats
        for(int count = 1; count <= noOfClassicSeats; count++){

            String seatNo = counter + "";
            seatNo = seatNo + ch;
            ch++;

            if((ch-'A')+1 == columns){
                ch = 'A';
                counter++;
            }
            TheaterSeat theaterSeat = new TheaterSeat();

            theaterSeat.setSeatNo(seatNo);
            theaterSeat.setSeatType(SeatType.BASIC);

            //unidirectional mapping
            theaterSeat.setTheater(theater);

            //bi directional mapping
            theater.getTheaterSeatList().add(theaterSeat);
        }

        //Setting the attributes for premium seats
        for(int count = 1; count <= noOfPremiumSeats; count++){

            String seatNo = counter + "";
            seatNo = seatNo + ch;
            ch++;

            if((ch-'A')+1 == columns){
                ch = 'A';
                counter++;
            }

            TheaterSeat theaterSeat = new TheaterSeat();

            theaterSeat.setSeatNo(seatNo);
            theaterSeat.setSeatType(SeatType.PREMIUM);

            //unidirectional mapping
            theaterSeat.setTheater(theater);

            //bi directional mapping
            theater.getTheaterSeatList().add(theaterSeat);
        }

        theaterRepository.save(theater);

        return "Theater Seats Saved Successfully";
    }

    public int countOfUniqueLocationsOfATheater() {
        List<Theater> theaterList = theaterRepository.findAll();
        Set<String> uniqueTheaters = new HashSet<>();

        for(Theater theater : theaterList) uniqueTheaters.add(theater.getLocation());

        return uniqueTheaters.size();
    }
}
