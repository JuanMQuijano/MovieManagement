package org.jquijano.proyects.MovieManagement.persistence.service;

import org.jquijano.proyects.MovieManagement.dto.request.SaveMovie;
import org.jquijano.proyects.MovieManagement.dto.response.GetMovie;
import org.jquijano.proyects.MovieManagement.persistence.entity.Movie;
import org.jquijano.proyects.MovieManagement.util.MovieGenre;

import java.util.List;

public interface MovieService {

    List<GetMovie> findAll();

    List<GetMovie> findAllByTitle(String title);

    List<GetMovie> findAllByGenre(MovieGenre genre);

    List<GetMovie> findAllByGenreAndTitle(MovieGenre genre, String title);

    GetMovie createOne(SaveMovie saveDto);

    GetMovie updateOneById(Long id, SaveMovie saveDto);

    void deleteOneById(Long id);

    GetMovie findOneById(Long id);
}
