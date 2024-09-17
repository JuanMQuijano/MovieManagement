package org.jquijano.proyects.MovieManagement.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.jquijano.proyects.MovieManagement.util.MovieGenre;

import java.io.Serializable;
import java.time.LocalDateTime;

public record SaveMovie(
        @NotBlank(message = "{generic.notblank}") @Size(min = 4, max = 255, message = "{generic.size}") String title,
        @NotBlank(message = "{generic.notblank}") @Size(min = 4, max = 255, message = "{generic.size}") String director,
        MovieGenre genre,
        @JsonProperty(value = "release_year")
        @Min(value = 1900, message = "{generic.min}") int releaseYear
//        @JsonProperty(value = "availability_end_time")
//        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
////                @Future //Que sean fechas mayores al día de hoy
//        @Past //Que sean fechas menores al día de hoy
//        LocalDateTime availabilityEndTime
) implements Serializable {
}
