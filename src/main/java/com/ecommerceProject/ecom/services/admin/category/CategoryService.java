package com.ecommerceProject.ecom.services.admin.category;

import com.ecommerceProject.ecom.dto.CategoryDto;
import com.ecommerceProject.ecom.entity.Category;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);

}
