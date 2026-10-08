package com.example.Portal.Service;

import com.example.Portal.Dto.ReviewRequestDto;
import com.example.Portal.Entity.Reviews;
import com.example.Portal.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    public ReviewRequestDto createReview(ReviewRequestDto dto){

        Reviews review = ReviewRequestDto.toEntity(dto);

        Reviews saved = reviewRepository.save(review);

        return ReviewRequestDto.toDto(saved);
    }

    public List<ReviewRequestDto> getAllReviews(){

        return reviewRepository.findAll()
                .stream()
                .map(ReviewRequestDto::toDto)
                .collect(Collectors.toList());
    }

    public List<ReviewRequestDto> getReviews(String employeeId){

        return reviewRepository.findByEmployeeId(employeeId)
                .stream()
                .map(ReviewRequestDto::toDto)
                .collect(Collectors.toList());
    }

    public ReviewRequestDto updateReview(String employeeId, ReviewRequestDto dto){

        Reviews review = reviewRepository.findByEmployeeId(employeeId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRating(dto.getRating());
        review.setGoals(dto.getGoals());
        review.setComments(dto.getComments());
        review.setStatus(dto.getStatus());

        Reviews saved = reviewRepository.save(review);

        return ReviewRequestDto.toDto(saved);
    }

    public String publishReview(String employeeId){

        Reviews review = reviewRepository.findByEmployeeId(employeeId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setStatus("published");

        reviewRepository.save(review);

        return "Review published successfully";
    }

}