package com.ecommerce.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.ecommerce.common.entity.Role;
import com.ecommerce.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 4);
		User userQuangTA = new User("dinosaurous9x@gmail.com", "quang2002", "Quang", "Tran Anh");
		userQuangTA.addRole(roleAdmin);
		
		User savedUser = userRepository.save(userQuangTA);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRoles() {
		User userRavi = new User("ravi@gmail.com", "ravi2002", "Ravi", "Kumar");
		Role roleEditor = new Role(6);
		Role roleAssistant = new Role(8);
		
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		User savedUser = userRepository.save(userRavi);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = userRepository.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userQuang = userRepository.findById(1).get();
		System.out.println(userQuang);
		assertThat(userQuang).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userQuang = userRepository.findById(1).get();
		userQuang.setEmail("quangtahe161550@gmail.com");
		userQuang.setEnabled(true);
		
		userRepository.save(userQuang);
	}
	
	@Test
	public void testUpdateRole() {
		User userRavi = userRepository.findById(2).get();
		Role roleEditor = new Role(6);
		Role roleSalesPerson = new Role(5);
		userRavi.getRoles().remove(roleEditor);
		userRavi.getRoles().add(roleSalesPerson);
		
		userRepository.save(userRavi);
	}
	
	@Test
	public void deleteUser() {
		Integer userId = 2;
		userRepository.deleteById(userId);
		
	}
}
