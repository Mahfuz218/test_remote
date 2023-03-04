package com.example.search_app.capston;

import com.example.search_app.capston.models.Role;
import com.example.search_app.capston.models.Users;
import com.example.search_app.capston.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class CapstonApplication implements CommandLineRunner {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(CapstonApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (usersRepository.findAll().size() == 0) {

			Role mngRole = new Role();
			mngRole.setName("MANAGER");

			Role userRole = new Role();
			userRole.setName("USER");


			Users mngUsers = new Users();
			mngUsers.setUsername("manager");
			mngUsers.setEmail("manager@gmail.com");
			mngUsers.setPassword(bCryptPasswordEncoder.encode("1234"));
			mngUsers.setEnabled(true);
			mngUsers.setAccountNonExpired(true);
			mngUsers.setAccountNonLocked(true);
			mngUsers.setCredentialsNonExpired(true);
			mngUsers.setRoleList(Set.of(mngRole));

			Users user = new Users();
			user.setUsername("user");
			user.setEmail("user@gmail.com");
			user.setPassword(bCryptPasswordEncoder.encode("1234"));
			user.setEnabled(true);
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
			user.setRoleList(Set.of(userRole));

			usersRepository.saveAll(List.of(mngUsers, user));

		}
	}
}
