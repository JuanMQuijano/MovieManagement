package org.jquijano.proyects.MovieManagement.dto.response;

import org.jquijano.proyects.MovieManagement.util.MovieGenre;

public record MovieSearchCriteria(
        String title,
        MovieGenre genre,
        Integer minReleaseYear,
        Integer maxReleaseYear,
        Integer minAverageRating
) {
}
