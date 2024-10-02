package org.jquijano.proyects.MovieManagement.persistence.service;

import org.jquijano.proyects.MovieManagement.dto.request.SaveRating;
import org.jquijano.proyects.MovieManagement.dto.response.GetCompleteRating;
import org.jquijano.proyects.MovieManagement.dto.response.GetMovie;
import org.jquijano.proyects.MovieManagement.dto.response.GetUser;
import org.jquijano.proyects.MovieManagement.persistence.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingService {

    Page<GetCompleteRating> findAll(Pageable pageable);

    Page<GetMovie.GetRating> findAllByMovieId(Long id, Pageable pageable);

    Page<GetUser.GetRating> findAllByUsername(String username, Pageable pageable);

    GetCompleteRating findOneById(Long id);

    GetCompleteRating createOne(SaveRating rating);

    GetCompleteRating updateOneById(Long id, SaveRating rating);

    void deleteOneById(Long id);

}
