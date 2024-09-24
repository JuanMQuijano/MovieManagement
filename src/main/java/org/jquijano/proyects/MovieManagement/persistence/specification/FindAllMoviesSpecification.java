package org.jquijano.proyects.MovieManagement.persistence.specification;

import jakarta.persistence.criteria.*;
import org.jquijano.proyects.MovieManagement.dto.response.MovieSearchCriteria;
import org.jquijano.proyects.MovieManagement.persistence.entity.Movie;
import org.jquijano.proyects.MovieManagement.persistence.entity.Rating;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FindAllMoviesSpecification implements Specification<Movie> {

    private MovieSearchCriteria movieSearchCriteria;


    public FindAllMoviesSpecification(MovieSearchCriteria searchCriteria) {
        this.movieSearchCriteria = searchCriteria;
    }


    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        //root = from Movie
        //query = criterios de la consulta en si misma
        //criteriaBuilder = permite construir predicados y expresiones para la consulta

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(this.movieSearchCriteria.title())) {
            Predicate titleLike = criteriaBuilder.like(root.get("title"), "%" + this.movieSearchCriteria.title() + "%");

            predicates.add(titleLike);
        }

        if (this.movieSearchCriteria.genre() != null) {
            Predicate genreEqual = criteriaBuilder.equal(root.get("genre"), this.movieSearchCriteria.genre());
            predicates.add(genreEqual);
        }

        if (this.movieSearchCriteria.minReleaseYear() != null && movieSearchCriteria.minReleaseYear().intValue() > 0) {
            Predicate releaseYearGraterThanEqual = criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"), this.movieSearchCriteria.minReleaseYear());
            predicates.add(releaseYearGraterThanEqual);
        }

        if (this.movieSearchCriteria.maxReleaseYear() != null && movieSearchCriteria.maxReleaseYear().intValue() > 0) {
            Predicate releaseYearLessThanEqual = criteriaBuilder.lessThanOrEqualTo(root.get("releaseYear"), this.movieSearchCriteria.maxReleaseYear());
            predicates.add(releaseYearLessThanEqual);
        }

        if (this.movieSearchCriteria.minAverageRating() != null && movieSearchCriteria.minAverageRating().intValue() > 0) {
            Subquery<Double> averageRatingSubquery = getAverageRatingSubquery(root, query, criteriaBuilder);

            Predicate averageRatingGreaterThanOrEqual = criteriaBuilder.greaterThanOrEqualTo(averageRatingSubquery, this.movieSearchCriteria.minAverageRating().doubleValue());

            predicates.add(averageRatingGreaterThanOrEqual);
        }

        Predicate[] predicatesAsArray = new Predicate[0];
        //Inserto los valores de la lista en el arreglo
        return criteriaBuilder.and(predicates.toArray(predicatesAsArray));
    }

    private static Subquery<Double> getAverageRatingSubquery(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Subquery<Double> averageRatingSubquery = query.subquery(Double.class);
        Root<Rating> ratingRoot = averageRatingSubquery.from(Rating.class);

        averageRatingSubquery.select(criteriaBuilder.avg(ratingRoot.get("rating")));

        Predicate movieIdEqual = criteriaBuilder.equal(root.get("id"), ratingRoot.get("movieId"));
        averageRatingSubquery.where(movieIdEqual);
        return averageRatingSubquery;
    }
}
