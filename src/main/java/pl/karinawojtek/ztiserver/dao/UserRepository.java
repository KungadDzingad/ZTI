package pl.karinawojtek.ztiserver.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.karinawojtek.ztiserver.models.database.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
}
