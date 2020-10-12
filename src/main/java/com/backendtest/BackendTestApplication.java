package com.backendtest;

import com.backendtest.model.User;
import com.backendtest.service.UserExtractor;
import com.backendtest.service.UserUnifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class BackendTestApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackendTestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<User> users = readUsers();
		UserUnifier userUnifier = new UserUnifier();
		List<User> resultUsers = userUnifier.process(users);
		printUsers(resultUsers);
	}

	private List<User> readUsers() {
		final List<User> userList = new ArrayList<>();
		UserExtractor userExtractor = new UserExtractor();
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			if ("q".equals(line)) {
				break;
			}
			Optional<User> user = userExtractor.extract(line);
			user.ifPresent(userList::add);
		}
		return userList;
	}

	private void printUsers(List<User> users) {
		users.forEach(user -> System.out.println(user.name() + " -> " + String.join(",", user.emails())));
	}
}
