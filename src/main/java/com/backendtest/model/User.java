package com.backendtest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
public class User {

    private String name;

    private Set<String> emails;

    public Set<String> emails () {
        if (Objects.isNull(emails)) {
            this.emails = new HashSet<>();
        }
        return this.emails;
    }
}
