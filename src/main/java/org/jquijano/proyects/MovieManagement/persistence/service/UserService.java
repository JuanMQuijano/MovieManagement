package org.jquijano.proyects.MovieManagement.persistence.service;

import org.jquijano.proyects.MovieManagement.dto.request.SaveUser;
import org.jquijano.proyects.MovieManagement.dto.response.GetUser;
import org.jquijano.proyects.MovieManagement.persistence.entity.User;

import java.util.List;

public interface UserService {

    List<GetUser> findAll();

    List<GetUser> findAllByName(String name);

    GetUser findOneByUsername(String username);

    GetUser createOne(SaveUser saveUser);

    GetUser updateOneByUsername(String username, SaveUser saveUser);

    void deleteOneByUsername(String username);

}
