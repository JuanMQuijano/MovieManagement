package org.jquijano.proyects.MovieManagement.persistence.service;

import org.jquijano.proyects.MovieManagement.persistence.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    List<User> findAllByName(String name);

    User findOneByUsername(String username);

    User createOne(User user);

    User updateOneByUsername(String username, User user);

    void deleteOneByUsername(String username);
}
