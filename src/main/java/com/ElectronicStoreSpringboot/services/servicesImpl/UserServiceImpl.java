package com.ElectronicStoreSpringboot.services.servicesImpl;

import com.ElectronicStoreSpringboot.Dtos.PageableResponse;
import com.ElectronicStoreSpringboot.Dtos.UserDTO;
import com.ElectronicStoreSpringboot.entities.User;
import com.ElectronicStoreSpringboot.exceptions.ResourceNotFoundException;
import com.ElectronicStoreSpringboot.repositories.UserRepository;
import com.ElectronicStoreSpringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    //method to convert UserDto to User entity
    public User userDtoTouser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }

    //method to convert User entity to UserDto
    public UserDTO userTouserDto(User user){
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }


    @Override
    public UserDTO lets_createUser(UserDTO userDTO) {

        String userId = UUID.randomUUID().toString();
        User newUser = userDtoTouser(userDTO);
        newUser = userRepository.save(newUser);
        UserDTO newUserDto = userTouserDto(newUser);
        return newUserDto;
    }

    @Override
    public UserDTO lets_updateUser(UserDTO userDTO, String userId) {

        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User is not found with this id :- " + userId));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setAbout(userDTO.getAbout());
        user.setGender(userDTO.getGender());
        user.setPassword(userDTO.getPassword());

        User updated_User = userRepository.save(user);
        UserDTO userDTO1 = userTouserDto(updated_User);
        return userDTO1;
    }

    @Override
    public void now_deleteUser(String userId) {

        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User is not found with this id :- " + userId));
        userRepository.delete(user);

    }

    @Override
    public UserDTO now_getUserById(String userId) {

        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User is not found with this id :- " + userId));
        UserDTO userDTO1 = userTouserDto(user);
        return userDTO1;
    }

    @Override
    public PageableResponse<UserDTO> now_getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Sorting and paging the list of all Users
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Fetching the paginated result from the repository
        Page<User> page = userRepository.findAll(pageable);

        // Convert the list of User entities to UserDTOs using the existing method
        List<UserDTO> userDTOs = page.getContent().stream()
                .map(this::userTouserDto) // Use the existing method to convert User to UserDTO
                .collect(Collectors.toList());

        // Create the PageableResponse object
        PageableResponse<UserDTO> response = PageableResponse.<UserDTO>builder()
                .content(userDTOs) // Set the content
                .pageNumber(page.getNumber()) // Current page number
                .pageSize(page.getSize()) // Size of the page
                .totalElements(page.getTotalElements()) // Total number of elements
                .totalPages(page.getTotalPages()) // Total number of pages
                .lastPage(page.isLast()) // Is this the last page?
                .build();

        return response;
    }

    @Override
    public UserDTO now_getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User is not found with this email :- " + email));
        UserDTO userDTO = userTouserDto(user);
        return userDTO;
    }

    @Override
    public UserDTO now_findUserByEmailAndPassword(String email, String password) {

        //let's validate inputs
        if(email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Email and password must not be null or blank");
        }

        //find the user by email and password
        Optional<User> userOptional = userRepository.findByEmailAndPassword(email, password);

        //if the user is not found, throw a ResourceNotFoundException
       User user = userOptional.orElseThrow(() -> new ResourceNotFoundException("User not found with provided email and password !!"));

       return userTouserDto(user);
    }

    @Override
    public List<UserDTO> lets_searchUser(String keyword) {

        // Validating the keyword
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword must not be null or blank");
        }

        //fetching users from the repository
        List<User> listOfUsers = userRepository.findByNameContaining(keyword);

        //here, mapping to DTOs by using Java Streams for conversion
        List<UserDTO> listOfUserDTO = listOfUsers.stream()
                .map(user -> userTouserDto(user))
                .collect(Collectors.toList());
        return listOfUserDTO;
    }
}
