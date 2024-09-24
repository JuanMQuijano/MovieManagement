package org.jquijano.proyects.MovieManagement.persistence.service;

import org.jquijano.proyects.MovieManagement.dto.response.MovieSearchCriteria;
import org.jquijano.proyects.MovieManagement.dto.request.SaveMovie;
import org.jquijano.proyects.MovieManagement.dto.response.GetMovie;

import java.util.List;

public interface MovieService {

    List<GetMovie> findAll(MovieSearchCriteria movieSearchCriteria);

//    List<GetMovie> findAllByTitle(String title);
//
//    List<GetMovie> findAllByGenre(MovieGenre genre);
//
//    List<GetMovie> findAllByGenreAndTitle(MovieGenre genre, String title);

    GetMovie createOne(SaveMovie saveDto);

    GetMovie updateOneById(Long id, SaveMovie saveDto);

    void deleteOneById(Long id);

    GetMovie findOneById(Long id);
}
