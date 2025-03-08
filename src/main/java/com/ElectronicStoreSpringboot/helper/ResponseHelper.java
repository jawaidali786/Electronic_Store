package com.ElectronicStoreSpringboot.helper;

import com.ElectronicStoreSpringboot.Dtos.PageableResponse;
import com.ElectronicStoreSpringboot.Dtos.UserDTO;
import com.ElectronicStoreSpringboot.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//this is generic helper class
@Component
public class ResponseHelper {

    @Autowired
    private static ModelMapper modelMapper = new ModelMapper();

    public static <T, R> PageableResponse<R> convertToPageableResponse(
            Page<T> page, Class<R> dtoClass) {

        if (page == null || dtoClass == null) {
            throw new IllegalArgumentException("Page and DTO class must not be null");
        }

        List<R> content = page.getContent().stream()
                .map(entity -> modelMapper.map(entity, dtoClass))
                .collect(Collectors.toList());

        PageableResponse<R> response = PageableResponse.<R>builder()
                .content(content)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .lastPage(page.isLast())
                .build();

        return response;
    }
}
