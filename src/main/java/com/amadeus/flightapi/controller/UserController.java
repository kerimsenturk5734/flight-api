package com.amadeus.flightapi.controller;

import com.amadeus.flightapi.dto.UserDto;
import com.amadeus.flightapi.dto.request.UserLoginRequest;
import com.amadeus.flightapi.dto.request.UserUpdateRequest;
import com.amadeus.flightapi.model.User;
import com.amadeus.flightapi.security.JwtTokenManager;
import com.amadeus.flightapi.service.UserService;
import com.amadeus.flightapi.util.SuccessResult;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping( "/v1/api/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;
    public UserController(UserService userService, JwtTokenManager jwtTokenManager) {
        this.userService = userService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest userLoginRequest){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity
                .created(uri)
                .body(userService.login(userLoginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user){
        String id = userService.add(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }


    @PreAuthorize("hasAuthority(@ROLES.ADMIN)")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId){
        UserDto userDto = userService.getUserById(userId);

        return ResponseEntity.ok(userDto);
    }

    @PreAuthorize("hasAuthority(@ROLES.ADMIN)")
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
        String userId = jwtTokenManager.extractUser(jwtTokenManager.extractToken(authHeader));
        String updatedUserId = userService.update(userId, userUpdateRequest);

        return ResponseEntity
                .accepted()
                .body(new SuccessResult(String.format("User updated with id : %s", updatedUserId)));
    }

    @PreAuthorize("hasAuthority(@ROLES.ADMIN)")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@Valid @PathVariable String userId){
        String deletedUserId = userService.delete(userId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new SuccessResult(String.format("User deleted with id : %s", deletedUserId)));
    }

}
