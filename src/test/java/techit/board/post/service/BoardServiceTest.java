package techit.board.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import techit.board.post.entity.Board;
import techit.board.post.service.form.SearchForm;

@SpringBootTest
class BoardServiceTest {
    @Autowired
    private BoardService boardService;


    @Test
    void findAll(){
        Page<Board> board = boardService.findAll(new SearchForm(), 0);
        for (Board board1 : board) {
            System.out.println("board1 = " + board1.getName());
        }
    }

}