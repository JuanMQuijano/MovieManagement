package org.jquijano.proyects.MovieManagement.persistence.service;

import org.jquijano.proyects.MovieManagement.persistence.entity.Rating;

import java.util.List;

public interface RatingService {

    List<Rating> findAll();

    List<Rating> findAllByMovieId(Long id);

    List<Rating> findAllByUsername(String username);

    Rating findOneById(Long id);

    Rating createOne(Rating rating);

    Rating updateOneById(Long id, Rating rating);

    void deleteOneById(Long id);

}
