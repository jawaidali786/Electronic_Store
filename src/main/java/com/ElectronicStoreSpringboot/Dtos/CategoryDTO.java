package com.ElectronicStoreSpringboot.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    @Id
    private String categoryId;

    private String name;

    @NotBlank(message = "title is required!!")
    @Size(min = 4, message = "title must be minimum 4 characters !!")
    private String title;

    @NotBlank(message = "Category description is required")
    @Size(max = 255, message = "Category description must be less than 255 characters")
    private String categoryDescription;

    //private String coverImage;
}
