package techit.board.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import techit.board.post.entity.Board;
import techit.board.post.service.form.ModifyForm;
import techit.board.post.service.form.SearchForm;

public interface BoardCustomRepository {
    Page<Board> findAllByTitle(SearchForm form, Pageable pageable);
    void updateBoard(ModifyForm form);
}
