package org.jquijano.proyects.MovieManagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jquijano.proyects.MovieManagement.util.MovieGenre;

import java.io.Serializable;
import java.util.List;

/*GetMovie es una clase, pero al ser record, no es necesario implementar sus métodos getters y setters, accedemos al valor de los atributos
por medio de GetMovie.director(); Dentro de esta clase creamos otro record para acceder a los atributos de un rating*/
public record GetMovie(
        Long id,
        String title,
        String director,
        MovieGenre genre,
        @JsonProperty(value = "release_year")
        int releaseYear,
        List<GetRating> ratings
) implements Serializable {

    public static record GetRating(
            Long id,
            int rating,
            String username
    ) implements Serializable {
    }

}
