package com.ElectronicStoreSpringboot.controllers;

import com.ElectronicStoreSpringboot.Dtos.ApiResponseMessage;
import com.ElectronicStoreSpringboot.Dtos.CategoryDTO;
import com.ElectronicStoreSpringboot.Dtos.PageableResponse;
import com.ElectronicStoreSpringboot.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategoryHandler(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO categoryDTO1 = categoryService.lets_createCategory(categoryDTO);
        return new ResponseEntity<>(categoryDTO1, HttpStatus.CREATED);
    }

    @PutMapping ("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategoryHandler(@RequestBody CategoryDTO categoryDTO, @PathVariable String categoryId) {
        CategoryDTO updatedCategory = categoryService.lets_updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategoryHandler(@PathVariable String categoryId) {
        categoryService.lets_deleteCategory(categoryId);

        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("Successfully deleted this category" + categoryId)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDTO>> getAllCategoriesHandler(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<CategoryDTO> response = categoryService.lets_getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        return new  ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryHandler(@PathVariable String categoryId) {
        CategoryDTO categoryDTO = categoryService.lets_getCategory(categoryId);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }
}
