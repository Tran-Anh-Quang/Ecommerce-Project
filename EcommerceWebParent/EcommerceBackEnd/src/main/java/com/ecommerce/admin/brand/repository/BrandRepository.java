package com.ecommerce.admin.brand.repository;

import com.ecommerce.common.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    public Long countById(Integer id);
}
