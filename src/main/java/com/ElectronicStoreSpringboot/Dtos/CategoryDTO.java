package com.ElectronicStoreSpringboot.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDTO {
    private String categoryId;
    @NotBlank(message = "title is required!!")
    @Size(min = 4, message = "title must be minimum 4 characters !!")
    private String title;
    @NotBlank(message = "Description required !!")
    private String description;
    private String coverImage;
}
