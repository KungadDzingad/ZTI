package pl.karinawojtek.ztiserver.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.karinawojtek.ztiserver.models.database.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phone);
    Optional<User> findByEmail(String email);
}
