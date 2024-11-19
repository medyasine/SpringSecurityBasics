package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/adminCategory")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "adminCategory";
    }

    @GetMapping("/{id}")
    public String getCategoryById(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        model.addAttribute("category", category.orElse(null));
        return "adminCategory";
    }

    @PostMapping
    public String createCategory(Category category,Model model) {
        Category savedCategory = categoryService.saveCategory(category);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("message", "Category created successfully");
        return "adminCategory";
    }

    @PostMapping("delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        categoryService.deleteCategory(id);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("message", "Category deleted successfully");
        return "adminCategory";
    }

    @PostMapping("update/{id}")
    public String updateCategory(@PathVariable Long id,  Category category, Model model) {
        Optional<Category> existingCategory = categoryService.getCategoryById(id);
        if (existingCategory.isPresent()) {
            category.setId(id);
            categoryService.saveCategory(category);
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("message", "Category updated successfully");
        } else {
            model.addAttribute("message", "Category not found");
        }
        return "adminCategory";
    }
}