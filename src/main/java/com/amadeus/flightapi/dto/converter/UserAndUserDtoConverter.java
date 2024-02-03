package com.amadeus.flightapi.dto.converter;

import com.amadeus.flightapi.dto.UserDto;
import com.amadeus.flightapi.model.User;
import com.amadeus.flightapi.util.ObjectConverter.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UserAndUserDtoConverter implements Convertable<User, UserDto> {

    @Override
    public UserDto convert(User user) {
        if(user == null)
            return null;

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getRole());
    }

    @Override
    public User deConvert(UserDto userDto) {
        if(userDto == null)
            return null;

        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getSurname(),
                "",
                userDto.getRole());
    }
}
