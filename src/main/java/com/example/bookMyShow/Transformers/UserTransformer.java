package com.example.bookMyShow.Transformers;

import com.example.bookMyShow.Dtos.RequestDtos.AddUserDto;
import com.example.bookMyShow.Dtos.ResponseDtos.UserResponseDto;
import com.example.bookMyShow.Models.User;

public class UserTransformer {
    public static User convertDtoToEntity(AddUserDto userDto){
        //Method 1
//        User user = new User();
//
//        //setting attributes
//        user.setName(userDto.getName());
//        user.setAge(userDto.getAge());
//        user.setMobNo(userDto.getMobNo());
//        user.setEmail(userDto.getEmailId());

        //Method 2
        User user = User.builder()
                .name(userDto.getName())
                .age(userDto.getAge())
                .mobNo(userDto.getMobNo())
                .email(userDto.getEmailId())
                .build();
        return user;
    }

    public static UserResponseDto convertEntityToDto(User user){
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .name(user.getName())
                .age(user.getAge())
                .mobNo(user.getMobNo())
                .build();

        return userResponseDto;
    }
}