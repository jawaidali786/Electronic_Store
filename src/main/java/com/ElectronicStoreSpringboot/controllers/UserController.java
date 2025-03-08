package com.ElectronicStoreSpringboot.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ElectronicStoreSpringboot.Dtos.ApiResponseMessage;
import com.ElectronicStoreSpringboot.Dtos.PageableResponse;
import com.ElectronicStoreSpringboot.Dtos.UserDTO;
import com.ElectronicStoreSpringboot.exceptions.ResourceNotFoundException;
import com.ElectronicStoreSpringboot.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

     @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

     @PostMapping
    public ResponseEntity<UserDTO> createUserHandler(@Valid @RequestBody UserDTO userDTO) {

        UserDTO userDTO1 = userService.lets_createUser(userDTO);
        logger.info("The user is created : {}", userDTO1.getUserId());
        return new ResponseEntity<>(userDTO1, HttpStatus.CREATED);

    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserHandler(@Valid @RequestBody UserDTO userDTO, @PathVariable String userId){

        UserDTO updateduserDTO = userService.lets_updateUser(userDTO,userId);
        logger.info("User updated : {} ", updateduserDTO.getUserId());
        return new ResponseEntity<>(updateduserDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUserHandler(@PathVariable String userId) {

        userService.now_deleteUser(userId);
        logger.info("User is deleted with ID : {}", userId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User is deleted with this Id")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDTO> getUserByIdHandler(@PathVariable String userId){
        logger.info("Fetching user with id: {}", userId);
        UserDTO userDTO = userService.now_getUserById(userId);
        logger.info("User fetched successfully : {}", userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
     * This is another way
     * GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserHandler(@PathVariable String userId) {
        logger.info("Fetching user with ID: {}", userId);
        UserDTO userDTO = userService.now_getUserById(userId);
        logger.info("User fetched successfully: {}", userDTO.getUser Id());

        // Wrap the response in a custom object
        ApiResponse<UserDTO> response = ApiResponse.<UserDTO>builder()
                .success(true)
                .message("User fetched successfully")
                .data(userDTO)
                .build();

        return ResponseEntity.ok(response);
    }
     */

    @GetMapping
    public ResponseEntity<PageableResponse<UserDTO>> getAllUsersHandler(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<UserDTO> response = userService.now_getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDTO>> searchUserHandler(@PathVariable String keywords) {

        logger.info("Searching for users with the keyword: {}", keywords);
        List<UserDTO> userDTOS = userService.lets_searchUser(keywords);
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmailHandler(@PathVariable String email) {
        logger.info("Fetching user with email: {}", email);
        UserDTO userDTO = userService.now_getUserByEmail(email);
        logger.info("User fetched successfully: {}", email);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<ApiResponseMessage> findUserByEmailAndPasswordHandler(
                    @RequestParam String email, @RequestParam String password) {

        try {
            // Call the service method
            UserDTO userDTO = userService.now_findUserByEmailAndPassword(email, password);

            //Create successful response message
            ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                    .message("User founded successfully.")
                    .success(true)
                    .status(HttpStatus.OK)
                    .build();

            //// Return the response with HTTP status 200 OK
            return new ResponseEntity<>(responseMessage,HttpStatus.OK);

        } catch (IllegalArgumentException e) {

            //Return 400 for invalid input
            ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                    .message(e.getMessage())
                    .success(false)
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        catch(ResourceNotFoundException e){
            //Return 404 for invalid input
            ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                        .message(e.getMessage())
                        .success(false)
                        .status(HttpStatus.NOT_FOUND)
                        .build();
                return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);
        }

    }
}








    

