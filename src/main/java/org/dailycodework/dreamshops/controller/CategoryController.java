package org.dailycodework.dreamshops.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dailycodework.dreamshops.exception.AlreadyExitsException;
import org.dailycodework.dreamshops.exception.ResourceNotFoundException;
import org.dailycodework.dreamshops.model.Category;
import org.dailycodework.dreamshops.response.ApiResponse;
import org.dailycodework.dreamshops.service.Category.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
@Tag(name = "Categories", description = "Operations about categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    @Operation(summary = "Get all categories", description = "Get all categories")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories=categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found!",categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:",INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Add category", description = "Add category")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
        try {
            Category theCategory=categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Added!",theCategory));
        } catch (AlreadyExitsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{id}/category")
    @Operation(summary = "Get category by id", description = "Get category by id")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            Category category=categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found!",category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }


    @GetMapping("category/{name}/category")
    @Operation(summary = "Get category by name", description = "Get category by name")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category category=categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found!",category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @DeleteMapping("/category/{id}/delete")
    @Operation(summary = "Delete category by id", description = "Delete category by id")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
           categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse("Found!",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @PutMapping("category/{id}/update")
    @Operation(summary = "Update category by id", description = "Update category by id")
    public ResponseEntity<ApiResponse> updateCategory( @RequestBody Category category,@PathVariable Long id) {
        try {
            Category updatedCategory=categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Updated!",updatedCategory));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
