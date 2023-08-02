package com.yevhent.microservicedemo.controller;

import com.yevhent.microservicedemo.domain.Tour;
import com.yevhent.microservicedemo.domain.TourRating;
import com.yevhent.microservicedemo.repository.TourRatingRepository;
import com.yevhent.microservicedemo.repository.TourRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
@AllArgsConstructor
public class TourRatingController {

    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TourRating createTourRating(@PathVariable(value = "tourId") String tourId, @RequestBody @Validated TourRating tourRating) {
        verifyTour(tourId);
        tourRating.setTourId(tourId);
        return tourRatingRepository.save(tourRating);
    }

    @GetMapping
    public Page<TourRating> getTourRatings(@PathVariable(value = "tourId") String tourId, Pageable pageable) {
        verifyTour(tourId);
        return tourRatingRepository.findByTourId(tourId, pageable);
    }

    public static final String AVERAGE_KEY = "average";

    @GetMapping(path = "/average")
    public Map<String, Double> getAverageScore(@PathVariable(value = "tourId") String tourId) {
        verifyTour(tourId);
        return Map.of(AVERAGE_KEY, tourRatingRepository.findByTourId(tourId).stream()
                .mapToInt(TourRating::getScore).average()
                .orElseThrow(() -> new NoSuchElementException("Tour has no Ratings")));
    }

    @PutMapping
    public TourRating updateCompletely(@PathVariable(value = "tourId") String tourId, @RequestBody @Validated TourRating tourRating) {
        TourRating tourRatingUpdate = verifyTourRating(tourId, tourRating.getCustomerId());
        tourRatingUpdate.setScore(tourRating.getScore());
        tourRatingUpdate.setComment(tourRating.getComment());
        return tourRatingRepository.save(tourRatingUpdate);
    }

    @PatchMapping
    public TourRating updatePartially(@PathVariable(value = "tourId") String tourId, @RequestBody @Validated TourRating tourRating) {
        TourRating tourRatingUpdate = verifyTourRating(tourId, tourRating.getCustomerId());
        if (tourRating.getScore() != null) {
            tourRatingUpdate.setScore(tourRating.getScore());
        }
        if (tourRating.getComment() != null) {
            tourRatingUpdate.setComment(tourRating.getComment());
        }
        return tourRatingRepository.save(tourRatingUpdate);
    }

    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") String tourId, @PathVariable(value = "customerId") int customerId) {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    private TourRating verifyTourRating(String tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tour-Rating pair for request(" + tourId + " for customer" + customerId));
    }

    private Tour verifyTour(String tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tour does not exist " + tourId));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        ex.printStackTrace();
        return ex.getMessage() + ex.fillInStackTrace();
    }
}
