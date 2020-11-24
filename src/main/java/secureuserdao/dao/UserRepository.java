package secureuserdao.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import secureuserdao.model.DAOUser;

@Repository
public interface UserRepository extends CrudRepository<DAOUser, Long> {

    DAOUser findByUsername(String username);

}