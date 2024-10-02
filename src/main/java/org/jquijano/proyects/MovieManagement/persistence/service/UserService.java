package org.jquijano.proyects.MovieManagement.persistence.service;

import org.jquijano.proyects.MovieManagement.dto.request.SaveUser;
import org.jquijano.proyects.MovieManagement.dto.response.GetUser;
import org.jquijano.proyects.MovieManagement.dto.response.GetUserStatistic;
import org.jquijano.proyects.MovieManagement.dto.response.UserSearchCriteria;
import org.jquijano.proyects.MovieManagement.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<GetUser> findAll(UserSearchCriteria userSearchCriteria, Pageable pageable);

//    List<GetUser> findAllByName(String name);

    /**
     *
     * @param username
     * @throws
     * @return
     */
    GetUserStatistic findOneByUsername(String username);

    GetUser createOne(SaveUser saveUser);

    GetUser updateOneByUsername(String username, SaveUser saveUser);

    void deleteOneByUsername(String username);

    /**
     * @param username
     * @return
     * @throws {@link org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException} if the given username doesn't exist
     */
    User findOneEntityByUsername(String username);
}
