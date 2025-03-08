package com.ElectronicStoreSpringboot.services;

import com.ElectronicStoreSpringboot.Dtos.CategoryDTO;
import com.ElectronicStoreSpringboot.Dtos.PageableResponse;

import java.util.SplittableRandom;

public interface CategoryService {

    CategoryDTO lets_createCategory(CategoryDTO categoryDTO);

    CategoryDTO lets_updateCategory(CategoryDTO categoryDTO, String categoryId);

    void lets_deleteCategory(String categoryId);

    PageableResponse<CategoryDTO> lets_getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir);

    CategoryDTO lets_getCategory(String categoryId);
}
