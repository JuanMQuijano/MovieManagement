package org.jquijano.proyects.MovieManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jquijano.proyects.MovieManagement.dto.request.SaveRating;
import org.jquijano.proyects.MovieManagement.dto.response.GetCompleteRating;
import org.jquijano.proyects.MovieManagement.persistence.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<GetCompleteRating>> findAll(Pageable pageable) {
        return ResponseEntity.ok(ratingService.findAll(pageable));
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<GetCompleteRating> findOneById(@PathVariable Long ratingId) {
        return ResponseEntity.ok(ratingService.findOneById(ratingId));
    }

    @PostMapping
    public ResponseEntity<GetCompleteRating> createOne(@Valid @RequestBody SaveRating saveRating, HttpServletRequest request) {

        GetCompleteRating newDto = ratingService.createOne(saveRating);
        String URL = request.getRequestURL().toString();
        URI newLocation = URI.create(URL + "/" + newDto.ratingId());

        return ResponseEntity.created(newLocation).body(newDto);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<GetCompleteRating> updateOneById(@PathVariable Long ratingId, @Valid @RequestBody SaveRating saveRating) {
        return ResponseEntity.ok(ratingService.updateOneById(ratingId, saveRating));
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long ratingId) {
        ratingService.deleteOneById(ratingId);
        return ResponseEntity.noContent().build() ;
    }
}
