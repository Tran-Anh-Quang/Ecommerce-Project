package com.ecommerce.admin.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.common.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
