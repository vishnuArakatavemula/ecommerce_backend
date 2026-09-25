package com.ecommerce.controller;
import org.springframework.data.domain.Page;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import jakarta.validation.Valid;
import com.ecommerce.dto.ProductDTO;
import java.util.ArrayList;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductDTO saveProduct(@Valid @RequestBody ProductDTO dto) {
        return productService.saveProduct(dto);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }
    // Replace only this method
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO dto) {

        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        return productService.deleteProduct(id);
    }

    // Rest of your code...
}
