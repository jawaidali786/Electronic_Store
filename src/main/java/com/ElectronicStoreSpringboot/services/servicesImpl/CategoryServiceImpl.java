package com.ElectronicStoreSpringboot.services.servicesImpl;

import com.ElectronicStoreSpringboot.Dtos.CategoryDTO;
import com.ElectronicStoreSpringboot.Dtos.PageableResponse;
import com.ElectronicStoreSpringboot.Dtos.UserDTO;
import com.ElectronicStoreSpringboot.entities.Category;
import com.ElectronicStoreSpringboot.entities.User;
import com.ElectronicStoreSpringboot.exceptions.ResourceNotFoundException;
import com.ElectronicStoreSpringboot.helper.ResponseHelper;
import com.ElectronicStoreSpringboot.repositories.CategoryRepository;
import com.ElectronicStoreSpringboot.services.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public Category categoryDtoToCategory(CategoryDTO categoryDTO) {
       return modelMapper.map(categoryDTO, Category.class);
    }

    public CategoryDTO categoryToCategoryDto(Category category) {
       return modelMapper.map(category, CategoryDTO.class);
    }

    //all business logic for category.
    @Override
    public CategoryDTO lets_createCategory(@Valid CategoryDTO categoryDTO) {

        String categoryId = UUID.randomUUID().toString();
        categoryDTO.setCategoryId(categoryId);

        Category category = categoryDtoToCategory(categoryDTO);
        category = categoryRepository.save(category);

        CategoryDTO categoryDTO1 = categoryToCategoryDto(category);
        return categoryDTO1;
    }

    @Override
    public CategoryDTO lets_updateCategory(CategoryDTO categoryDTO, String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                     .orElseThrow(() -> new ResourceNotFoundException("The category is not founded with this id : " + categoryId));

        //set the category details here
        category.setTitle(categoryDTO.getTitle());
        category.setDescription(categoryDTO.getCategoryDescription());

        Category updatedCategory = categoryRepository.save(category);
        return categoryToCategoryDto(category);

    }

    @Override
    public void lets_deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("The category is not founded with this id : " + categoryId));
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDTO> lets_getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Sorting and paging the list of all Users
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Fetching the paginated result from the repository
        Page<Category> page = categoryRepository.findAll(pageable);

        PageableResponse<CategoryDTO> pageableResponse = ResponseHelper.convertToPageableResponse(page, CategoryDTO.class);

        return pageableResponse;
    }

    public CategoryDTO lets_getCategory(String categoryId) {

        if (categoryId == null || categoryId.trim().isEmpty()) {
            throw new IllegalArgumentException("Category ID must not be null or empty");
        }

        logger.info("Fetching category with ID: {}" , categoryId);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("The category is not founded with this id : " + categoryId));
        return categoryToCategoryDto(category);
    }
}
