package com.yevhent.microservicedemo.controller;

import com.yevhent.microservicedemo.domain.Tour;
import com.yevhent.microservicedemo.domain.TourRating;
import com.yevhent.microservicedemo.repository.TourRatingRepository;
import com.yevhent.microservicedemo.repository.TourRepository;
import com.yevhent.microservicedemo.web.TourRatingDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
@AllArgsConstructor
public class TourRatingController {

    private TourRatingRepository tourRatingRepository;
    private TourRepository tourRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TourRating createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated TourRatingDto tourRatingDto) {
        Tour tour = verifyTour(tourId);
        return tourRatingRepository.save(new TourRating(tour, tourRatingDto.getCustomerId(), tourRatingDto.getScore()));
    }

    @GetMapping
    public Page<TourRatingDto> getTourRatings(@PathVariable(value = "tourId") int tourId, Pageable pageable) {
        verifyTour(tourId);
        Page<TourRating> ratings = tourRatingRepository.findByPkTourId(tourId, pageable);
        return new PageImpl<>(ratings.get().map(TourRatingDto::new).collect(Collectors.toList()), pageable, ratings.getTotalElements());
    }

    public static final String AVERAGE_KEY = "average";

    @GetMapping(path = "/average")
    public Map<String, Double> getAverageScore(@PathVariable(value = "tourId") int tourId) {
        verifyTour(tourId);
        return Map.of(AVERAGE_KEY,
                tourRatingRepository.findByPkTourId(tourId).stream()
                        .mapToInt(TourRating::getScore).average()
                        .orElseThrow(() -> new NoSuchElementException("Tour has no Ratings")));
    }

    @PutMapping
    public TourRatingDto updateCompletely(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated TourRatingDto tourRatingDto) {
        TourRating rating = verifyTourRating(tourId, tourRatingDto.getCustomerId());
        rating.setScore(tourRatingDto.getScore());
        rating.setComment(tourRatingDto.getComment());
        return new TourRatingDto(tourRatingRepository.save(rating));
    }

    @PatchMapping
    public TourRatingDto updateSelectively(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated TourRatingDto tourRatingDto) {
        TourRating tourRating = verifyTourRating(tourId, tourRatingDto.getCustomerId());
        if (tourRatingDto.getScore() != null) {
            tourRating.setScore(tourRatingDto.getScore());
        }
        if (tourRatingDto.getComment() != null) {
            tourRating.setComment(tourRatingDto.getComment());
        }
        return new TourRatingDto(tourRatingRepository.save(tourRating));
    }

    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "customerId") int customerId) {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tour-Rating pair for request("
                        + tourId + " for customer" + customerId));
    }

    private Tour verifyTour(int tourId) throws NoSuchElementException {
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
