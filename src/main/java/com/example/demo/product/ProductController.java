package com.example.demo.product;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> FetchProducts() {
        return productService.GetProducts();
    }

    @GetMapping(path = "{productId}")
    public Optional<Product> FetchProductById(@PathVariable("productId") String productId) {
        return productService.GetProductDetail(productId);
    }

    @PutMapping(path = "{productId}")
    public void PutProduct(@PathVariable("productId") String productId, @RequestBody Product product) {
        productService.UpdateProduct(productId, product);
    }

    @PostMapping
    public void PostProduct(@RequestBody Product product) {
        productService.CreateProduct(product);
    }

    @DeleteMapping(path = "{productId}")
    public void DeleteProduct(@PathVariable("productId") String productId) {
        productService.RemoveProduct(productId);
    }
}