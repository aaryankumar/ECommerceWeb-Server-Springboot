package com.ecommerceProject.ecom.repository;

import com.ecommerceProject.ecom.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //In angle brackets of JpaRepository<> we have to mention the class name of entity(class) and the data type of it's primary key
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
