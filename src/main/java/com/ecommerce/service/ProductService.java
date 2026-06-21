package com.ecommerce.service;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entity.Product;
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

    // Save Product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Get All Products (DTO)
    public List<ProductDTO> getAllProducts() {

        List<Product> products = productRepository.findAll();

        List<ProductDTO> dtoList = new ArrayList<>();

        for (Product product : products) {

            ProductDTO dto = new ProductDTO();

            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());

            dtoList.add(dto);
        }

        return dtoList;
    }

    // Get Product By Id (DTO)
    public ProductDTO getProductById(Long id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());

        return dto;
    }

    // Update Product
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());

            return productRepository.save(existingProduct);
        }

        return null;
    }

    // Delete Product
    public String deleteProduct(Long id) {

        productRepository.deleteById(id);

        return "Product deleted successfully";
    }

    // Pagination
    public Page<Product> getProductsWithPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findAll(pageable);
    }

    // Sorting
    public List<Product> getAllProductsSorted(String field, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();

        return productRepository.findAll(sort);
    }
}