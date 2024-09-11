package org.jquijano.proyects.MovieManagement.persistence.service.impl;

import org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException;
import org.jquijano.proyects.MovieManagement.persistence.entity.User;
import org.jquijano.proyects.MovieManagement.persistence.repository.UserCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Override
    public List<User> findAll() {
        return userCrudRepository.findAll();
    }

    @Override
    public List<User> findAllByName(String name) {
        return userCrudRepository.findByNameContaining(name);
    }

    @Override
    public User findOneByUsername(String username) {
        return userCrudRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("[user: " + username + "]"));
    }

    @Override
    public User createOne(User user) {
        return userCrudRepository.save(user);
    }

    @Override
    public User updateOneByUsername(String username, User newUser) {
        User oldUser = this.findOneByUsername(username);

        oldUser.setName(newUser.getName());
        oldUser.setPassword(newUser.getPassword());

        return userCrudRepository.save(oldUser);
    }

    @Override
    public void deleteOneByUsername(String username) {
        if (userCrudRepository.deleteByUsername(username) != 1) {
            throw new ObjectNotFoundException("[user: " + username + "]");

        }
    }
}
