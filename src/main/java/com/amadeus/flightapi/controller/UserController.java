package com.amadeus.flightapi.controller;

import com.amadeus.flightapi.dto.UserDto;
import com.amadeus.flightapi.dto.request.UserUpdateRequest;
import com.amadeus.flightapi.model.User;
import com.amadeus.flightapi.service.UserService;
import com.amadeus.flightapi.util.SuccessResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(name = "v1/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user){
        String id = userService.add(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId){
        UserDto userDto = userService.getUserById(userId);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(){
        //Get the all users
        List<UserDto> userDtoList = userService.getAllUsers();

        //Place the userDto into Result wrapper object
        return ResponseEntity.ok(userDtoList);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest,
                                        @RequestHeader("Authorization") String authHeader){
        //Resolve user from JWT Token
        String updatedUserId = userService.update("", userUpdateRequest);

        return ResponseEntity
                .accepted()
                .body(new SuccessResult(String.format("User updated with id : %s", updatedUserId)));
    }

    @DeleteMapping(name = "/delete/{userId}")
    public ResponseEntity<?> deleteUser(@Valid @PathVariable String userId){
        String deletedUserId = userService.delete(userId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new SuccessResult(String.format("User deleted with id : %s", deletedUserId)));
    }

}
