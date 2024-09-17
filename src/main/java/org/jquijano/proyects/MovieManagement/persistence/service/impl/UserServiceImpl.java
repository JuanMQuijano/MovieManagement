package org.jquijano.proyects.MovieManagement.persistence.service.impl;

import org.jquijano.proyects.MovieManagement.dto.request.SaveUser;
import org.jquijano.proyects.MovieManagement.dto.response.GetUser;
import org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException;
import org.jquijano.proyects.MovieManagement.mapper.UserMapper;
import org.jquijano.proyects.MovieManagement.persistence.entity.Movie;
import org.jquijano.proyects.MovieManagement.persistence.entity.User;
import org.jquijano.proyects.MovieManagement.persistence.repository.MovieCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.repository.UserCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.service.UserService;
import org.jquijano.proyects.MovieManagement.persistence.service.validator.PasswordValidator;
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
    public List<GetUser> findAll() {
        return UserMapper.toGetDtoList(userCrudRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetUser> findAllByName(String name) {
        return UserMapper.toGetDtoList(userCrudRepository.findByNameContaining(name));
    }

    @Override
    @Transactional(readOnly = true)
    public GetUser findOneByUsername(String username) {
        return UserMapper.toGetDto(this.findOneEntityByUsername(username));
    }

    @Transactional(readOnly = true)
    private User findOneEntityByUsername(String username) {
        return userCrudRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("[user: " + username + "]"));
    }

    @Override
    public GetUser createOne(SaveUser saveDto) {
        PasswordValidator.validatePassword(saveDto.password(), saveDto.passwordRepeated());
        return UserMapper.toGetDto(userCrudRepository.save(UserMapper.toEntity(saveDto)));
    }

    @Override
    public GetUser updateOneByUsername(String username, SaveUser saveDto) {
        PasswordValidator.validatePassword(saveDto.password(), saveDto.passwordRepeated());

        User oldUser = this.findOneEntityByUsername(username);

        UserMapper.updateEntity(oldUser, saveDto);

        return UserMapper.toGetDto(userCrudRepository.save(oldUser));
    }

    @Override
    public void deleteOneByUsername(String username) {
        if (userCrudRepository.deleteByUsername(username) != 1) {
            throw new ObjectNotFoundException("[user: " + username + "]");

        }
    }
}
