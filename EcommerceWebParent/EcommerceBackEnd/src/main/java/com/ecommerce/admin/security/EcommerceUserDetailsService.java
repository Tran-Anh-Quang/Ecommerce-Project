package com.ecommerce.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ecommerce.admin.user.repository.UserRepository;
import com.ecommerce.common.entity.User;

public class EcommerceUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if (user != null) {
            return new EcommerceUserDetails(user);
        }
        throw new UsernameNotFoundException("Could not find any user with email " + email);
    }

}
