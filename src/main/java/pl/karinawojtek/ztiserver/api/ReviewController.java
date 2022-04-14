package pl.karinawojtek.ztiserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.karinawojtek.ztiserver.exception.custom.ObjectByIdNotFoundException;
import pl.karinawojtek.ztiserver.services.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteReciew(@PathVariable long id) throws ObjectByIdNotFoundException {
        reviewService.deleteReview(reviewService.findReview(id));
    }

}
