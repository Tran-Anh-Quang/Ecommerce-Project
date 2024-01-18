package com.ecommerce.admin.user;

import com.ecommerce.admin.category.repository.CategoryRepository;
import com.ecommerce.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateRootCategory(){
        Category category = new Category("Software");
        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory(){
        Category parent = new Category(10);
        Category subCategory = new Category("RAM", parent);
        Category savedCategory = categoryRepository.save(subCategory);

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetCategory(){
        Category category = categoryRepository.findById(1).get();
        System.out.println(category.getName());

        Set<Category> children = category.getChildren();

        for (Category subCategory : children){
            System.out.println(subCategory.getName());
        }

        assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintHierarchicalCategories(){
        Iterable<Category> categories = categoryRepository.findAll();

        for (Category category : categories){
            if (category.getParent() == null){
                System.out.println(category.getName());

                Set<Category> children = category.getChildren();

                for (Category subCategory : children){
                    System.out.println("---" + subCategory.getName());
                    printChildren(subCategory, 1);
                }
            }
        }
    }

    public void printChildren(Category parent, int subLevel){

        int newSubLevel = subLevel + 1;

        Set<Category> children = parent.getChildren();
        for (Category subCategory : children){
            for (int i = 0; i < newSubLevel; i++){
                System.out.println("---");
            }
            System.out.println(subCategory.getName());

            printChildren(subCategory, newSubLevel);
        }
    }
}
