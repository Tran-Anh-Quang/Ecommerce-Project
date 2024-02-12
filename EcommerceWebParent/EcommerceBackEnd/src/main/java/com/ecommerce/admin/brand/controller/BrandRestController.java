package com.ecommerce.admin.brand.controller;

import com.ecommerce.admin.brand.exception.BrandNotFoundException;
import com.ecommerce.admin.brand.exception.BrandNotFoundRestException;
import com.ecommerce.admin.brand.service.BrandService;
import com.ecommerce.admin.dto.CategoryDTO;
import com.ecommerce.common.entity.Brand;
import com.ecommerce.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class BrandRestController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/brands/{id}/categories")
    public List<CategoryDTO> listCategoriesByBrand(@PathVariable(name = "id") Integer brandId) throws BrandNotFoundRestException {

        List<CategoryDTO> listCategories = new ArrayList<>();

        try {
            Brand brand = brandService.get(brandId);
            Set<Category> categories = brand.getCategories();
            for (Category category : categories){
                CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());
                listCategories.add(dto);
            }
            return listCategories;
        } catch (BrandNotFoundException e){
            throw new BrandNotFoundRestException();
        }
    }
}
