package pl.karinawojtek.ztiserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karinawojtek.ztiserver.dao.ReviewRepository;
import pl.karinawojtek.ztiserver.exception.custom.ObjectByIdNotFoundException;
import pl.karinawojtek.ztiserver.exception.custom.WrongReviewMarkException;
import pl.karinawojtek.ztiserver.models.database.*;
import pl.karinawojtek.ztiserver.models.request.CreateReviewRequest;

import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    public Review findReview(long id) throws ObjectByIdNotFoundException{
        Optional<Review> reviewOpt = reviewRepository.findById(id);
        if(reviewOpt.isEmpty())
            throw new ObjectByIdNotFoundException("No Review With ID " + id);
        return reviewOpt.get();
    }


    public void createAuctionReview(Auction auction, User user, CreateReviewRequest reviewRequest) throws WrongReviewMarkException {
        if(reviewRequest.getMark() <=0 || reviewRequest.getMark() > 5)
            throw new WrongReviewMarkException("Mark must be value from 1 to 5");

        AuctionReview review = new AuctionReview();
        review.setReviewedAuction(auction);
        review.setCreator(user);
        review.setDescription(reviewRequest.getDescription());
        review.setMark(reviewRequest.getMark());
        reviewRepository.save(review);
    }

    public void createUserReview(User user, User reviewed, CreateReviewRequest reviewRequest) throws WrongReviewMarkException {
        if(reviewRequest.getMark() <=0 || reviewRequest.getMark() > 5)
            throw new WrongReviewMarkException("Mark must be value from 1 to 5");

        UserReview review = new UserReview();
        review.setReviewedUser(reviewed);
        review.setCreator(user);
        review.setDescription(reviewRequest.getDescription());
        review.setMark(reviewRequest.getMark());
        reviewRepository.save(review);
    }

    public void deleteReview(Review review){
        reviewRepository.delete(review);
    }

}
