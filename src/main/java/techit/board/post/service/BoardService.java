package techit.board.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.board.post.entity.Board;
import techit.board.post.exception.NotFoundBoard;
import techit.board.post.repository.BoardCrudRepository;
import techit.board.post.repository.BoardForm;
import techit.board.post.service.form.ModifyForm;
import techit.board.post.service.form.SearchForm;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardCrudRepository boardRepository;

    @Transactional
    public Long save(BoardForm form) {
        Board savedBoard = boardRepository.save(Board.cretaeBoard(form));
        return savedBoard.getId();
    }

    public BoardForm findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new NotFoundBoard("요청하신 보드를 찾을수 없습니다."));
        return BoardForm.createBoardForm(board);
    }
    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
    @Transactional
    public void modifyBoard(ModifyForm form) {
        boardRepository.updateBoard(form);
    }
    public Page<Board> findAll(SearchForm form, int offset) {
        Pageable pageable =  PageRequest.of(offset, 5, Sort.by(Sort.Direction.DESC,"updated_at"));
        return boardRepository.findAllByTitle(form,pageable);
    }
    @Transactional
    public void deleteAll() {
        boardRepository.deleteAll();
    }
}
