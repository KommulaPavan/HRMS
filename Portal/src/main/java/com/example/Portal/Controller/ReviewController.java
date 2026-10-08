package com.example.Portal.Controller;

import com.example.Portal.Dto.ReviewRequestDto;
import com.example.Portal.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/performance")
public class ReviewController {

    @Autowired
    ReviewService reviewService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ReviewRequestDto createReview(@RequestBody ReviewRequestDto dto){
        return reviewService.createReview(dto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ReviewRequestDto> getAllReviews(){
        return reviewService.getAllReviews();
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping()
//    public List<ReviewRequestDto> gReview(String employeeId){
//        return reviewService.getReviews(employeeId);
//
//    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{employeeId}")
    public List<ReviewRequestDto> Review(@PathVariable String employeeId){
        return reviewService.getReviews(employeeId);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{employeeId}")
    public ReviewRequestDto updateReview(
            @PathVariable String employeeId,
            @RequestBody ReviewRequestDto dto){

        return reviewService.updateReview(employeeId,dto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{employeeId}/publish")
    public String publishReview(@PathVariable String employeeId){
        return reviewService.publishReview(employeeId);
    }

    @GetMapping("/reports")
    public List<ReviewRequestDto> reports(){
        return reviewService.getAllReviews();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/kpis")
    public Map<String,Object> kpis(){

        List<ReviewRequestDto> reviews = reviewService.getAllReviews();

        int total = reviews.size();

        double avg = reviews.stream()
                .mapToInt(ReviewRequestDto::getRating)
                .average()
                .orElse(0);

        return Map.of(
                "totalReviews", total,
                "averageRating", avg
        );
    }
}
