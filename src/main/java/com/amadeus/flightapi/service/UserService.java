package com.amadeus.flightapi.service;

import com.amadeus.flightapi.dto.UserDto;
import com.amadeus.flightapi.dto.converter.UserAndUserDtoConverter;
import com.amadeus.flightapi.dto.request.UserUpdateRequest;
import com.amadeus.flightapi.exception.UserAlreadyExistException;
import com.amadeus.flightapi.exception.UserNotFoundException;
import com.amadeus.flightapi.model.User;
import com.amadeus.flightapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserAndUserDtoConverter userAndUserDtoConverter;
    public UserService(UserRepository userRepository, UserAndUserDtoConverter userAndUserDtoConverter) {
        this.userRepository = userRepository;
        this.userAndUserDtoConverter = userAndUserDtoConverter;
    }

    public UserDto getUserById(String userId){
         return userAndUserDtoConverter
                 .convert(
                         userRepository
                                 .findById(userId)
                                 .orElseThrow(() ->
                                         new UserNotFoundException(String.format("User not found by id : %s", userId)))
                 );
    }

    private User getRawUserById(String userId){
        return userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User not found by id : %s", userId)));
    }

    public List<UserDto> getAllUsers(){
        return userRepository
                .findAll()
                .stream()
                .map(userAndUserDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public String add(User user){
        if(userRepository.existsById(user.getId()))
            return userRepository.save(user).getId();

        throw new UserAlreadyExistException(String.format("User already exists with id : %s", user.getId()));
    }

    public String update(String userId, UserUpdateRequest userUpdateRequest){
        User user = getRawUserById(userId);

        user.setName(userUpdateRequest.name().orElse(user.getName()));
        user.setSurname(userUpdateRequest.surname().orElse(user.getSurname()));
        user.setPassword(userUpdateRequest.name().orElse(user.getPassword()));

        return userRepository.save(user).getId();
    }

    public String delete(String userId){
        userRepository.deleteById(getRawUserById(userId).getId());
        return userId;
    }

}
