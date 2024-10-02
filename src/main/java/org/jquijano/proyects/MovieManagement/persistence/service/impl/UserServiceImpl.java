package org.jquijano.proyects.MovieManagement.persistence.service.impl;

import org.jquijano.proyects.MovieManagement.dto.request.SaveUser;
import org.jquijano.proyects.MovieManagement.dto.response.GetUser;
import org.jquijano.proyects.MovieManagement.dto.response.GetUserStatistic;
import org.jquijano.proyects.MovieManagement.dto.response.UserSearchCriteria;
import org.jquijano.proyects.MovieManagement.exception.ObjectNotFoundException;
import org.jquijano.proyects.MovieManagement.mapper.UserMapper;
import org.jquijano.proyects.MovieManagement.persistence.entity.Movie;
import org.jquijano.proyects.MovieManagement.persistence.entity.User;
import org.jquijano.proyects.MovieManagement.persistence.repository.MovieCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.repository.RatingCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.repository.UserCrudRepository;
import org.jquijano.proyects.MovieManagement.persistence.service.UserService;
import org.jquijano.proyects.MovieManagement.persistence.service.validator.PasswordValidator;
import org.jquijano.proyects.MovieManagement.persistence.specification.FindAllUsersSpecification;
import org.jquijano.proyects.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private MovieCrudRepository movieCrudRepository;

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Autowired
    private RatingCrudRepository ratingCrudRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<GetUser> findAll(UserSearchCriteria userSearchCriteria, Pageable pageable) {
        FindAllUsersSpecification findAllUsersSpecification = new FindAllUsersSpecification(userSearchCriteria);
        Page<User> entities = userCrudRepository.findAll(findAllUsersSpecification, pageable);
        return entities.map(UserMapper::toGetDto);
    }

    @Transactional(readOnly = true)
    @Override
    public User findOneEntityByUsername(String username) {
        return userCrudRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("[user: " + username + "]"));
    }

    @Override
    @Transactional(readOnly = true)
    public GetUserStatistic findOneByUsername(String username) {
        User user = this.findOneEntityByUsername(username);
        Double avg = ratingCrudRepository.getAvgRatingByUserId(user.getId());
        int min = ratingCrudRepository.getMinRatingByUserId(user.getId());
        int max = ratingCrudRepository.getMaxRatingByUserId(user.getId());
        return UserMapper.toGetUserStatisticDto(this.findOneEntityByUsername(username), avg, min, max);
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
