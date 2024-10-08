package univ_rouen.fr.uefullstack.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ_rouen.fr.uefullstack.Model.APIResponseModel;
import univ_rouen.fr.uefullstack.Model.Category;
import univ_rouen.fr.uefullstack.Model.UpdateCategoryRequest;
import univ_rouen.fr.uefullstack.Service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/AddCategory")
    public ResponseEntity<APIResponseModel<Category>> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        APIResponseModel<Category> response = new APIResponseModel<>(
                "Category created successfully", true, createdCategory
        );
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<APIResponseModel<Page<Category>>> GetAllCategories(Pageable pageable) {
        Page<Category> categories = categoryService.getAllCategories(pageable);
        APIResponseModel<Page<Category>> response = new APIResponseModel<>(
                "Categories retrieved successfully", true, categories
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/UpdateCategory/{id}")
    public ResponseEntity<APIResponseModel<Category>> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequest updateRequest) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, updateRequest.getCategory(), updateRequest.getSelectedChildrenIds());
            APIResponseModel<Category> response = new APIResponseModel<>(
                    "Category updated successfully", true, updatedCategory
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            APIResponseModel<Category> errorResponse = new APIResponseModel<>(
                    "Error updating category: " + e.getMessage(), false, null
            );
            return ResponseEntity.status(500).body(errorResponse);
        }
    }


}
