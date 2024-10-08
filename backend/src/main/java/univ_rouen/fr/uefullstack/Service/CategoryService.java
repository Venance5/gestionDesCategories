package univ_rouen.fr.uefullstack.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import univ_rouen.fr.uefullstack.Model.Category;
import univ_rouen.fr.uefullstack.Repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    
    public Category createCategory(Category category) {
        if (category.getParent() != null && category.getParent().getId().equals(category.getId())) {
            throw new IllegalArgumentException("A category cannot be its own parent");
        }
        return categoryRepository.save(category);
    }
    
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }


    @Transactional
    public Category updateCategory(Long id, Category category, List<Long> selectedChildrenIds) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        existingCategory.setName(category.getName());
        existingCategory.setParent(category.getParent());

        // Créer un ensemble des IDs des enfants existants
        Set<Long> existingChildrenIds = existingCategory.getChildren().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        // Mise à jour des enfants
        for (Long childId : selectedChildrenIds) {
            if (!existingChildrenIds.contains(childId)) {
                Category childCategory = categoryRepository.findById(childId)
                        .orElseThrow(() -> new RuntimeException("Child category not found with id: " + childId));
                childCategory.setParent(existingCategory);
                existingCategory.getChildren().add(childCategory);
            }
        }

        // Supprimer les enfants qui ne sont plus sélectionnés
        existingCategory.getChildren().removeIf(child -> !selectedChildrenIds.contains(child.getId()));

        return categoryRepository.save(existingCategory);
    }


    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}

