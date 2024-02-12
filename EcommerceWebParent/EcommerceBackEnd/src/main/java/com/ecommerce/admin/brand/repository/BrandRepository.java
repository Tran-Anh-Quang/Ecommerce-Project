package com.ecommerce.admin.brand.repository;

import com.ecommerce.common.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    public Long countById(Integer id);

    @Query("SELECT NEW Brand(b.id, b.name) b FROM Brand b ORDER BY b.name ASC")
    public List<Brand> findAll();
}
