package techit.board.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import techit.board.post.entity.Board;

public interface BoardCrudRepository extends CrudRepository<Board, Long>, PagingAndSortingRepository<Board,Long>,BoardCustomRepository {
    Page<Board> findByTitle(String title, Pageable pageable);

}
