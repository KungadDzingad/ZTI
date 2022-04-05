package pl.karinawojtek.ztiserver.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.karinawojtek.ztiserver.models.database.UserRole;

@Repository
public interface RoleRepository extends CrudRepository<UserRole,String> {
    UserRole findByRoleName(String roleName);
}
