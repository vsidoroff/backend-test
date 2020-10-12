package com.backendtest.service;

import com.backendtest.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserUnifier {
    public List<User> process(List<User> users) {
        Map<String, String> emailUserMap = new HashMap<>();
        Map<String, Set<String>> userEmailMap = new HashMap<>();
        for (User user : users) {
            String userName = null;
            for (String email : user.emails()) {
                if (emailUserMap.containsKey(email)) {
                    userName = emailUserMap.get(email);
                    break;
                }
            }
            if (Objects.nonNull(userName)) {
                Set<String> emailSet = userEmailMap.get(userName);
                emailSet.addAll(user.emails());
            } else {
                userName = user.name();
                userEmailMap.put(user.name(), new HashSet<>(user.emails()));
            }
            for (String email : user.emails()) {
                emailUserMap.put(email, userName);
            }
        }
        return userEmailMap.keySet()
                .stream()
                .map(userName -> new User(userName, userEmailMap.get(userName)))
                .collect(Collectors.toList());
    }
}