package com.ecommerceProject.ecom.services.admin.category;

import com.ecommerceProject.ecom.dto.CategoryDto;
import com.ecommerceProject.ecom.entity.Category;
import com.ecommerceProject.ecom.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryRepository.save(category);
    }

}
