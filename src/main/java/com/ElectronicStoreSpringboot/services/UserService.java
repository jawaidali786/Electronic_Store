package com.ElectronicStoreSpringboot.services;

import com.ElectronicStoreSpringboot.Dtos.PageableResponse;
import com.ElectronicStoreSpringboot.Dtos.UserDTO;

import java.util.List;

public interface UserService {
    //creating a new user.
    UserDTO lets_createUser(UserDTO userDTO);
    //updating a existing user.
    UserDTO lets_updateUser(UserDTO userDTO, String userId);
    //deleting a existing user.
    void now_deleteUser(String userId);
    //get a user.
    UserDTO now_getUserById(String id);
    //get a list of users.
    PageableResponse<UserDTO> now_getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
    //get a single user by email.
    UserDTO now_getUserByEmail(String email);
    //it's optional to find the valid email.
    UserDTO now_findUserByEmailAndPassword(String email, String password);
    //Search a user.
    List<UserDTO> lets_searchUser(String keyword);

}
