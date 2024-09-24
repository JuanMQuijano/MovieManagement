package org.jquijano.proyects.MovieManagement.persistence.service;

import org.jquijano.proyects.MovieManagement.dto.response.MovieSearchCriteria;
import org.jquijano.proyects.MovieManagement.dto.request.SaveMovie;
import org.jquijano.proyects.MovieManagement.dto.response.GetMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MovieService {

    Page<GetMovie> findAll(MovieSearchCriteria movieSearchCriteria, Pageable pageable);

    GetMovie createOne(SaveMovie saveDto);

    GetMovie updateOneById(Long id, SaveMovie saveDto);

    void deleteOneById(Long id);

    GetMovie findOneById(Long id);
}
