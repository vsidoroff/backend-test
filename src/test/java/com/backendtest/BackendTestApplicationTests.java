package com.backendtest;

import com.backendtest.model.User;
import com.backendtest.service.UserExtractor;
import com.backendtest.service.UserUnifier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BackendTestApplicationTests {

	@Test
	void testUserExtractorRightInput() {
		UserExtractor userExtractor = new UserExtractor();
		String[] inputLines =  new String[] {
				"user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru",
				"user2 -> foo@gmail.com, ups@pisem.net",
				"user3 -> xyz@pisem.net, vasya@pupkin.com",
				"user4 -> ups@pisem.net, aaa@bbb.ru",
				"user5 -> xyz@pisem.net"
		};
		final List<User> userList = new ArrayList<>();
		for (String line : inputLines) {
			Optional<User> user = userExtractor.extract(line);
			user.ifPresent(userList::add);
		}
		assertEquals(userList.size(), 5);
	}

	@Test
	void testUserExtractorWrongInput() {
		UserExtractor userExtractor = new UserExtractor();
		String[] inputLines =  new String[] {
				"user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru",
				"user2 : foo@gmail.com, ups@pisem.net",
				"user3 -> xyz@pisem.net, vasya@pupkin.com",
				"user4 -> ups@pisem.net, aaa@bbb.ru",
				""
		};
		final List<User> userList = new ArrayList<>();
		for (String line : inputLines) {
			Optional<User> user = userExtractor.extract(line);
			user.ifPresent(userList::add);
		}
		assertEquals(userList.size(), 3);
	}

	@Test
	void testUserExtractor() {
		UserExtractor userExtractor = new UserExtractor();
		String[] inputLines =  new String[] {
				"user1 -> xxx@ya.ru, foo@gmail.com",
				"user2 -> foo@gmail.com, ups@pisem.net"
		};
		final List<User> userList = new ArrayList<>();
		for (String line : inputLines) {
			Optional<User> user = userExtractor.extract(line);
			user.ifPresent(userList::add);
		}
		assertEquals(userList.size(), 2);
		User user1 = userList.get(0);
		assertEquals(user1.name(), "user1");
		assertTrue(user1.emails().containsAll(Arrays.asList("xxx@ya.ru", "foo@gmail.com")));
		User user2 = userList.get(1);
		assertEquals(user2.name(), "user2");
		assertTrue(user2.emails().containsAll(Arrays.asList("foo@gmail.com", "ups@pisem.net")));
	}

	@Test
	void testUserUnifier() {
		UserExtractor userExtractor = new UserExtractor();
		String[] inputLines =  new String[] {
				"user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru",
				"user2 -> foo@gmail.com, ups@pisem.net",
				"user3 -> xyz@pisem.net, vasya@pupkin.com",
				"user4 -> ups@pisem.net, aaa@bbb.ru",
				"user5 -> xyz@pisem.net"
		};
		final List<User> users = new ArrayList<>();
		for (String line : inputLines) {
			Optional<User> user = userExtractor.extract(line);
			user.ifPresent(users::add);
		}
		assertEquals(users.size(), 5);
		UserUnifier userUnifier = new UserUnifier();
		List<User> resultUsers = userUnifier.process(users);
		assertEquals(resultUsers.size(), 2);
		//users.forEach(user -> System.out.println("Input user.name=" + user.name() + " user.emails=" + String.join(",", user.emails())));
		//resultUsers.forEach(user -> System.out.println("Result user.name=" + user.name() + " user.emails=" + String.join(",", user.emails())));
	}

	@Test
	void testUserUnifier3User() {
		UserExtractor userExtractor = new UserExtractor();
		String[] inputLines =  new String[] {
				"user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru",
				"user2 -> foo2@gmail.com, ups2@pisem.net",
				"user3 -> xyz@pisem.net, vasya@pupkin.com",
		};
		final List<User> users = new ArrayList<>();
		for (String line : inputLines) {
			Optional<User> user = userExtractor.extract(line);
			user.ifPresent(users::add);
		}
		assertEquals(users.size(), 3);
		UserUnifier userUnifier = new UserUnifier();
		List<User> resultUsers = userUnifier.process(users);
		assertEquals(resultUsers.size(), 3);
//		users.forEach(user -> System.out.println("Input user.name=" + user.name() + " user.emails=" + String.join(",", user.emails())));
//		resultUsers.forEach(user -> System.out.println("Result user.name=" + user.name() + " user.emails=" + String.join(",", user.emails())));
	}
}
