package org.jquijano.proyects.MovieManagement.persistence.repository;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.jquijano.proyects.MovieManagement.persistence.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingCrudRepository extends JpaRepository<Rating, Long>, JpaSpecificationExecutor<Rating> {

    Page<Rating> findByMovieId(Long id, Pageable pageable);

    Page<Rating> findByUserUsername(String username, Pageable pageable);

    @Query("SELECT r FROM Rating r JOIN r.user u WHERE u.username = ?1")
    Page<Rating> findByUsername(String username, Pageable pageable);

    boolean existsByMovieIdAndUserUsername(Long movieId, String username);

    @Query("SELECT r.id FROM Rating r WHERE r.movieId = ?1 AND r.user.username = ?2")
    Long getRatingIdByMovieIdAndUserUsername(Long movieId, String username);

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.movieId = ?1")
    Double getAvgRatingByMovieId(Long movieId);

    @Query("SELECT MIN(r.rating) FROM Rating r WHERE r.movieId = ?1")
    int getMinRatingByMovieId(Long movieId);

    @Query("SELECT MAX(r.rating) FROM Rating r WHERE r.movieId = ?1")
    int getMaxRatingByMovieId(Long movieId);

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.userId = ?1")
    Double getAvgRatingByUserId(Long userId);

    @Query("SELECT MIN(r.rating) FROM Rating r WHERE r.userId = ?1")
    int getMinRatingByUserId(Long userId);

    @Query("SELECT MAX(r.rating) FROM Rating r WHERE r.userId = ?1")
    int getMaxRatingByUserId(Long userId);
}
