package com.avalanchelabs.app;

import com.avalanchelabs.app.entity.User;
import com.avalanchelabs.app.repository.RoleRepo;
import com.avalanchelabs.app.repository.UserRepo;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppApplicationTests {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Test
	void userRepositoryTest() {

		User user = new User();
		user.setUsername("Maksim");
		user.setPassword("123456789");
		user.setRole(roleRepo.findByName("ROLE_ADMIN"));
		userRepo.save(user);
		Assert.notNull(userRepo.findByUsername("Maksim"));
	}

}
