package com.ecommerce.admin.category.service;

import com.ecommerce.admin.category.repository.CategoryRepository;
import com.ecommerce.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    public static final int CATEGORIES_PER_PAGE = 5;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listAll(){
        return (List<Category>) categoryRepository.findAll();
    }

    public List<Category> listCategoriesUsedInForm(){
        List<Category> categoriesUsedInForm = new ArrayList<>();

        Iterable<Category> categoriesInDB = categoryRepository.findAll();

        for (Category category : categoriesInDB){
            if (category.getParent() == null){
                categoriesUsedInForm.add(Category.copyIdAndName(category));

                Set<Category> children = category.getChildren();

                for (Category subCategory : children){
                    String name = "--" + subCategory.getName();
                    categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

                    listChildren(categoriesUsedInForm, subCategory, 1);
                }
            }
        }
        return categoriesUsedInForm;
    }

    private void listChildren(List<Category> categoriesUsedInForm, Category parent, int subLevel){
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children){
            String name = "";
            for (int i = 0; i < newSubLevel; i++){
                name += "--";
            }
            name += subCategory.getName();
            categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

            listChildren(categoriesUsedInForm, subCategory, newSubLevel);
        }
    }

    public Page<Category> listByPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, CATEGORIES_PER_PAGE);
        return categoryRepository.findAll(pageable);
    }

    public void updateCategoryEnableStatus(Integer id, boolean enabled) {
        categoryRepository.updateEnabledStatus(id, enabled);
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }
}
