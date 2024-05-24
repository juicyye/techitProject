package techit.board.post.repository;

import org.springframework.data.repository.CrudRepository;
import techit.board.post.entity.User;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
}
