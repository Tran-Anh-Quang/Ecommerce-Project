package com.ecommerce.customer.repository;

import com.ecommerce.common.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c Where c.email = ?1")
    public Customer findByEmail(String email);

    @Query("SELECT c FROM Customer c Where c.verificationCode = ?1")
    public Customer findByVerificationCode(String code);

    @Query("UPDATE Customer c SET c.enabled = true WHERE c.id = ?1")
    @Modifying
    public void enable(Integer id);
}
