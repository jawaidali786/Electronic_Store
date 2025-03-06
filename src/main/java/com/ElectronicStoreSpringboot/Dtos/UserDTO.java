package com.ElectronicStoreSpringboot.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String userId;

    @Size(max = 20, min = 3, message = "Invalid Name")
    @NotBlank(message = "Name is required !!")
    private String name;

    @Size(min = 8, max = 13, message = "Invalid Name")
    @NotBlank(message = "password is required !!")
    private String password;

    @NotBlank(message = "Email is required !!")
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message="Invalid User Email !!")
    private String email;

    @Pattern(regexp = "^(Male|Female|Other)$", message = "Invalid gender")
    private String gender;
    @NotBlank(message = "Write something about you")
    private String about;
    //@Size(max = 25)
    //private String imageName;


    private Set<RoleDTO> roles = new HashSet<>();
}
