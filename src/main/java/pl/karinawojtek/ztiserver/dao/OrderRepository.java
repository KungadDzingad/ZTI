package pl.karinawojtek.ztiserver.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.karinawojtek.ztiserver.models.database.Order;


@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {
}
