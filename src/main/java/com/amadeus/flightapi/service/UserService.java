package com.amadeus.flightapi.service;

import com.amadeus.flightapi.dto.UserDto;
import com.amadeus.flightapi.dto.converter.UserAndUserDtoConverter;
import com.amadeus.flightapi.dto.request.UserLoginRequest;
import com.amadeus.flightapi.dto.request.UserUpdateRequest;
import com.amadeus.flightapi.dto.response.UserLoginResponse;
import com.amadeus.flightapi.exception.UserAlreadyExistException;
import com.amadeus.flightapi.exception.UserNotFoundException;
import com.amadeus.flightapi.model.User;
import com.amadeus.flightapi.model.enums.UserRole;
import com.amadeus.flightapi.repository.UserRepository;
import com.amadeus.flightapi.security.JwtTokenManager;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserAndUserDtoConverter userAndUserDtoConverter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;
    private final Logger logger = Logger.getLogger(UserService.class.getName());
    public UserService(UserRepository userRepository, UserAndUserDtoConverter userAndUserDtoConverter,
                       PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                       JwtTokenManager jwtTokenManager) {
        this.userRepository = userRepository;
        this.userAndUserDtoConverter = userAndUserDtoConverter;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenManager = jwtTokenManager;
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
            throw new UserAlreadyExistException(String.format("User already exists with id : %s", user.getId()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    public String update(String userId, UserUpdateRequest userUpdateRequest){
        User user = getRawUserById(userId);

        user.setName(userUpdateRequest.name().orElse(user.getName()));
        user.setSurname(userUpdateRequest.surname().orElse(user.getSurname()));

        //Set don't updated password as initial this pwd already encrypted
        String pwd = user.getPassword();

        //Encrypt the raw password coming from optional if presents
        if(userUpdateRequest.password().isPresent())
            pwd = passwordEncoder.encode(userUpdateRequest.password().get());

        user.setPassword(pwd);

        return userRepository.save(user).getId();
    }

    public String delete(String userId){
        userRepository.deleteById(getRawUserById(userId).getId());
        return userId;
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest){
        Authentication auth = authenticationManager
                .authenticate(new
                        UsernamePasswordAuthenticationToken(userLoginRequest.userId(), userLoginRequest.password()));

        if(auth.isAuthenticated())
            return new UserLoginResponse(
                    jwtTokenManager.generate(userLoginRequest.userId()), getUserById(userLoginRequest.userId()));

        throw new UsernameNotFoundException("User Id or password incorrect");
    }


    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void addDefaultUsers() {
        //Add admin
        add(new User(
                "admin",
                "admin",
                "admin",
                "admin", UserRole.ADMIN));

        //Add user
        add(new User(
                "user",
                "user",
                "user",
                "user", UserRole.USER));

        logger.info("Users added");
    }

}
