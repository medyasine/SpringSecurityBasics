package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showProducts(Map<String, Object> model) {
        model.put("product", new Product());
        model.put("products", productService.getAllProducts());
        model.put("categories", categoryService.getAllCategories());
        return "adminProduct";
    }

    @GetMapping("/adminProduct")
    public String getAllProducts(Map<String, Object> model) {
        model.put("product", new Product());
        model.put("products", productService.getAllProducts());
        model.put("categories", categoryService.getAllCategories());
        return "adminProduct";
    }

    @GetMapping("/adminProduct/filter")
    public String filterProducts(@RequestParam String name, Map<String, Object> model) {
        List<Product> products = productService.getProductsByName(name);
        model.put("products", products);
        model.put("categories", categoryService.getAllCategories());
        return "adminProduct";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        model.addAttribute("product", product.orElse(null));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "adminProduct";
    }

    @PostMapping("delete/")
    public String deleteProduct(@PathVariable Long id, Model model) {
        productService.deleteProduct(id);
        model.addAttribute("product", new Product());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("message", "Product deleted successfully");
        return "adminProduct";
    }

    private String saveImage(MultipartFile image) {
        String imageName = image.getOriginalFilename();
        Path imagePath = Paths.get("src/main/resources/static/uploads", imageName);
        try {    Files.createDirectories(imagePath.getParent());
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return imageName;
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateProduct(
            Model model,
            @RequestParam("productImage") MultipartFile image,
            @ModelAttribute Product product) {
        if (!image.isEmpty()) {
            String imageName = saveImage(image);
            product.setImage(imageName);
        }
        productService.saveProduct(product);
        model.addAttribute("product", new Product());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "adminProduct";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createProduct(
            @RequestParam("productImage") MultipartFile image,
            @ModelAttribute Product product,
            Model model) {
        if (!image.isEmpty()) {
            String imageName = saveImage(image);
            product.setImage(imageName);
        }
        productService.saveProduct(product);
        Product p = new Product();
        model.addAttribute("product", p);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("message", "Product created successfully");
        model.addAttribute("categories", categoryService.getAllCategories());

        return "adminProduct";
    }
}
