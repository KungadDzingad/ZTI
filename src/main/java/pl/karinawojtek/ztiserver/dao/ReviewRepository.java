package pl.karinawojtek.ztiserver.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.karinawojtek.ztiserver.models.database.Review;

@NoRepositoryBean
public interface ReviewRepository<T extends Review> extends CrudRepository<T, Long> {
}
