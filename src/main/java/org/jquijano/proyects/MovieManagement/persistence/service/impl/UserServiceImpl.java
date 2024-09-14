package org.jquijano.proyects.MovieManagement.persistence.service.impl;

import org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException;
import org.jquijano.proyects.MovieManagement.persistence.entity.Movie;
import org.jquijano.proyects.MovieManagement.persistence.entity.User;
import org.jquijano.proyects.MovieManagement.persistence.repository.MovieCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.repository.UserCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.service.UserService;
import org.jquijano.proyects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userCrudRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllByName(String name) {
        return userCrudRepository.findByNameContaining(name);
    }

    @Override
    @Transactional(readOnly = true)
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
