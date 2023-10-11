package com.example.bookMyShow.Services;

import com.example.bookMyShow.Dtos.RequestDtos.AddUserDto;
import com.example.bookMyShow.Dtos.RequestDtos.UserTicketRequestDto;
import com.example.bookMyShow.Dtos.ResponseDtos.TicketListResponseDto;
import com.example.bookMyShow.Dtos.ResponseDtos.UserResponseDto;
import com.example.bookMyShow.Exceptions.NoUserFoundException;
import com.example.bookMyShow.Models.Ticket;
import com.example.bookMyShow.Models.User;
import com.example.bookMyShow.Repositories.UserRepository;
import com.example.bookMyShow.Transformers.TicketTransformer;
import com.example.bookMyShow.Transformers.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public String addUser(AddUserDto userDto) {
        //Calling the transformer function
        User user = UserTransformer.convertDtoToEntity(userDto);
        userRepository.save(user);
        return "User has been successfully added";
    }

    public UserResponseDto getOldestUser()throws NoUserFoundException{
        //Prevents you from exposing the PK
        //Prevents infinite recursion incase it occurs

        //get all the users from rep
        List<User> userList = userRepository.findAll();
        int maxAge = 0;
        User userAns = null;

        //iterate over all the users to get the user with the max age
        for(User user : userList){
            if(user.getAge() > maxAge){
                maxAge = user.getAge();;
                userAns = user;
            }
        }

        if(userAns == null){
            throw new NoUserFoundException("No User Found");
        }

        //We need to transform this userAns entity to Dto
        //calling transformer
        UserResponseDto userResponseDto = UserTransformer.convertEntityToDto(userAns);

        return userResponseDto;
    }

    public List<User> findUsersWithGreaterAge(Integer age) {
        return userRepository.findUsersWithGreaterAge(age);
    }

    public TicketListResponseDto ticketsBookedByAParticularUser(UserTicketRequestDto ticketRequestDto) throws NoUserFoundException {
        int userId = ticketRequestDto.getUserId();
        Optional<User> userOptional = userRepository.findById(userId);

        //Validation check
        if(userOptional.isEmpty()){
            throw new NoUserFoundException("User Id Is Incorrect!!");
        }

        User user = userOptional.get();
        TicketListResponseDto ticketListResponseDto = TicketTransformer.convertTicketEntityToResponseDto(user);
        return ticketListResponseDto;
    }
}
