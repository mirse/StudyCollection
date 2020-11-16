package com.wdz.module_architecture.dagger;

import javax.inject.Inject;

public class User {
    private String name = "名字";

    @Inject
    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
