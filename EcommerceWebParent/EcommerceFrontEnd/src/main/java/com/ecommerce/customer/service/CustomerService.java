package com.ecommerce.customer.service;

import com.ecommerce.common.entity.Country;
import com.ecommerce.customer.repository.CustomerRepository;
import com.ecommerce.setting.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Country> listAllCountries(){
        return countryRepository.findAllByOrderByNameAsc();
    }
}
