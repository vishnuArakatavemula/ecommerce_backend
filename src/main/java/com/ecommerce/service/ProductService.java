package com.ecommerce.service;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    // =========================
    // CREATE PRODUCT
    // =========================

    public ProductDTO saveProduct(ProductDTO dto) {

        // Find category using categoryId
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Create Product entity
        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(category);

        // Save Product
        Product savedProduct = productRepository.save(product);

        // Convert Entity to DTO
        return convertToDTO(savedProduct);
    }


    // =========================
    // GET ALL PRODUCTS
    // =========================

    public List<ProductDTO> getAllProducts() {

        List<Product> products = productRepository.findAll();

        List<ProductDTO> dtoList = new ArrayList<>();

        for (Product product : products) {

            ProductDTO dto = convertToDTO(product);

            dtoList.add(dto);
        }

        return dtoList;
    }


    // =========================
    // GET PRODUCT BY ID
    // =========================

    public ProductDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return convertToDTO(product);
    }


    // =========================
    // UPDATE PRODUCT
    // =========================

    public ProductDTO updateProduct(Long id, ProductDTO dto) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Find new category
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingProduct.setName(dto.getName());
        existingProduct.setDescription(dto.getDescription());
        existingProduct.setPrice(dto.getPrice());
        existingProduct.setStock(dto.getStock());
        existingProduct.setImageUrl(dto.getImageUrl());
        existingProduct.setCategory(category);

        Product updatedProduct = productRepository.save(existingProduct);

        return convertToDTO(updatedProduct);
    }


    // =========================
    // DELETE PRODUCT
    // =========================

    public String deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            return "Product not found";
        }

        productRepository.deleteById(id);

        return "Product deleted successfully";
    }


    // =========================
    // PAGINATION
    // =========================

    public Page<ProductDTO> getProductsWithPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(this::convertToDTO);
    }


    // =========================
    // SORTING
    // =========================

    public List<ProductDTO> getAllProductsSorted(
            String field,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();

        List<Product> products = productRepository.findAll(sort);

        List<ProductDTO> dtoList = new ArrayList<>();

        for (Product product : products) {

            dtoList.add(convertToDTO(product));
        }

        return dtoList;
    }


    // =========================
    // ENTITY → DTO
    // =========================

    private ProductDTO convertToDTO(Product product) {

        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setImageUrl(product.getImageUrl());

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }

        return dto;
    }
}