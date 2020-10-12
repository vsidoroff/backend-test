package com.backendtest.service;

import com.backendtest.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserExtractor {
    public Optional<User> extract(String userStr) {
        String[] splitted = userStr.split("->");
        if (splitted.length == 2) {
            String userName = splitted[0].trim();
            Set<String> emailSet = new HashSet<>(Arrays.asList(splitted[1].trim().split("\\s*,\\s*")));
            if (!emailSet.isEmpty()) {
                return Optional.of(new User(userName, emailSet));
            }
        }
        return Optional.empty();
    }
}
