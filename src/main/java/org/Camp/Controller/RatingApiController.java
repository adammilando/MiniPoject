package org.Camp.Controller;

import org.Camp.Model.Entities.Rating;
import org.Camp.Model.Response.CommonResponse;
import org.Camp.Model.Response.SuccessResponse;
import org.Camp.Service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/ratings")
public class RatingApiController {

    private final RatingService ratingService;

    public RatingApiController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity getAllRatings() {
        List<Rating> ratings = ratingService.findAll();
        CommonResponse commonResponse = new SuccessResponse<>("Success Get All Rating", ratings);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity getRatingById(@PathVariable("id") Long id) {
        Rating rating = ratingService.findById(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Find Rating With Id "+id, rating);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping
    public ResponseEntity createRating(@RequestBody Rating rating) {
        Rating createdRating = ratingService.save(rating);
        CommonResponse commonResponse = new SuccessResponse<>("Success Created New Rating", createdRating);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity updateRating(@PathVariable("id") Long id, @RequestBody Rating rating) {
         rating.setId(id);
         Rating updatedRating = ratingService.update(rating);
         CommonResponse commonResponse = new SuccessResponse<>("Success Update Rating With id "+id,updatedRating);
         return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteRating(@PathVariable("id") Long id) {
        Rating rating = ratingService.findById(id);
        ratingService.delete(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Delete Rating With Id " + id, rating);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}

