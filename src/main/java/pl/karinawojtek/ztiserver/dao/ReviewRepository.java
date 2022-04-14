package pl.karinawojtek.ztiserver.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.karinawojtek.ztiserver.models.database.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
}
