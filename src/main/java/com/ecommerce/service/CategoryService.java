package com.ecommerce.service;

import com.ecommerce.entity.Category;
import com.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Save Category
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Get All Categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get Category By Id
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Update Category
    public Category updateCategory(Long id, Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    // Delete Category
    public String deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return "Category deleted successfully";
    }
}