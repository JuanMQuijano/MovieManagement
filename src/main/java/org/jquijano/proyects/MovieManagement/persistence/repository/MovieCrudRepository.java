package org.jquijano.proyects.MovieManagement.persistence.repository;

import org.jquijano.proyects.MovieManagement.persistence.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MovieCrudRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

}
