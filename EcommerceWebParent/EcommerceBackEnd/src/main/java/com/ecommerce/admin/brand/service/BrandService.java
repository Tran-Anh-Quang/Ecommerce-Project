package com.ecommerce.admin.brand.service;

import com.ecommerce.admin.brand.exception.BrandNotFoundException;
import com.ecommerce.admin.brand.repository.BrandRepository;
import com.ecommerce.common.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    public static final int BRANDS_PER_PAGE = 5;
    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> listAll(){
        return (List<Brand>) brandRepository.findAll();
    }

    public Page<Brand> listByPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, BRANDS_PER_PAGE);
        return brandRepository.findAll(pageable);
    }

    public void delete(Integer id) throws BrandNotFoundException {
        Long countById = brandRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new BrandNotFoundException("Could not find any brand with ID: " + id);
        }

        brandRepository.deleteById(id);
    }

}
