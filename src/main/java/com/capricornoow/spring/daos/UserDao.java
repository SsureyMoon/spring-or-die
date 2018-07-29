package com.capricornoow.spring.daos;

import com.capricornoow.spring.domain.User;

import java.util.List;

public interface UserDao {
    void add(final User user);
    User get(String id);
    List<User> getAll();
    void delete(final String id);
    void deleteAll();
    int getCount();
}
